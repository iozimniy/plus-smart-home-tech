package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.products.*;
import ru.yandex.practicum.repository.StoreRepository;

import java.util.UUID;

import static ru.yandex.practicum.mapper.ProductMapper.mapToDto;
import static ru.yandex.practicum.mapper.ProductMapper.mapToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreRepository repository;

    @Override
    public Page<ProductDto> getProducts(ProductCategory category, Integer page, Integer size, String sort) {
        log.info("Request for receive products with category {}, page {}, size {}, sort {}", category, page, size, sort);

        PageRequest pageable;

        if (sort != null && page != null) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
            return repository.findByProductCategoryAndProductState(
                            category,
                            ProductState.ACTIVE, pageable)
                    .map(ProductMapper::mapToDto);
        } else if (sort == null && page == null) {
            pageable = PageRequest.of(0, 10);
        } else {
            pageable = PageRequest.of(page, size);
        }

        return repository.findByProductState(ProductState.ACTIVE, pageable)
                .map(ProductMapper::mapToDto);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Request for create product {}", productDto);
        return mapToDto(repository.save(mapToEntity(productDto)));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException {
        log.info("Request for update product with id {}", productDto.getProductId());
        log.debug("Request for update product with id {}", productDto);
        if (!repository.existsById(productDto.getProductId())) {
            throw new ProductNotFoundException("Not found by UUID " + productDto.getProductId());
        }

        Product newProduct = Product.builder()
                .id(productDto.getProductId())
                .productName(productDto.getProductName())
                .description(productDto.getDescription())
                .image(productDto.getImageSrc())
                .quantityState(productDto.getQuantityState())
                .productState(productDto.getProductState())
                .productCategory(productDto.getProductCategory())
                .price(productDto.getPrice())
                .build();

        return mapToDto(repository.save(newProduct));
    }

    @Override
    @Transactional
    public Boolean deactivateProduct(UUID productId) throws ProductNotFoundException {
        log.info("Request for deactivate product with id {]", productId);
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found by UUID " + productId));

        product.setProductState(ProductState.DEACTIVATE);
        repository.save(product);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateQuantityState(UUID productId, QuantityState quantityState) throws ProductNotFoundException {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found by UUID " + productId));

        product.setQuantityState(quantityState);
        repository.save(product);
        return true;
    }

    @Override
    public ProductDto getProductById(UUID productId) throws ProductNotFoundException {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found by UUID " + productId));
        return mapToDto(product);
    }
}
