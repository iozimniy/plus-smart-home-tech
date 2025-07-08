package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.products.*;
import ru.yandex.practicum.repository.StoreRepository;

import java.util.Collection;
import java.util.UUID;

import static ru.yandex.practicum.mapper.ProductMapper.mapToDto;
import static ru.yandex.practicum.mapper.ProductMapper.mapToEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreRepository repository;

    @Override
    public Collection<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        log.info("Request for receive products with category {} and pageable {}", category, pageable);
        return repository.findByProductCategoryAndProductState(category, ProductState.ACTIVE, pageable)
                .stream().map(ProductMapper::mapToDto).toList();

    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Request for create product {}", productDto);
        return mapToDto(repository.save(mapToEntity(productDto)));
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException {
        log.info("Request for update product with id {}", productDto.getProductId());
        log.debug("Request for update product with id {}", productDto);
        if (!repository.existsById(productDto.getProductId())) {
            throw new ProductNotFoundException("Not found by UUID " + productDto.getProductId());
        }

        Product newProduct = Product.builder()
                .id(productDto.getProductId())
                .name(productDto.getDescription())
                .description(productDto.getDescription())
                .image(productDto.getImageSrc())
                .quantityState(productDto.getQuantityState())
                .productState(productDto.getProductState())
                .productCategory(productDto.getProductCategory())
                .build();

        return mapToDto(repository.save(newProduct));
    }

    @Override
    public Boolean deactivateProduct(UUID productId) throws ProductNotFoundException {
        log.info("Request for deactivate product with id {]", productId);
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found by UUID " + productId));

        product.setProductState(ProductState.DEACTIVATE);
        repository.save(product);
        return true;
    }

    @Override
    public Boolean updateQuantityState(UUID productId, QuantityState quantityState) throws ProductNotFoundException {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found by UUID " + productId));

        product.setQuantityState(quantityState);
        return true;
    }

    @Override
    public ProductDto getProductById(UUID productId) throws ProductNotFoundException {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Not found by UUID " + productId));
        return mapToDto(product);
    }
}
