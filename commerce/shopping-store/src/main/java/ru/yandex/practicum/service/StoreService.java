package ru.yandex.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.products.ProductCategory;
import ru.yandex.practicum.products.ProductDto;
import ru.yandex.practicum.products.ProductNotFoundException;
import ru.yandex.practicum.products.QuantityState;

import java.util.Collection;
import java.util.UUID;

public interface StoreService {
    Collection<ProductDto> getProducts(ProductCategory category, Pageable pageable);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException;

    Boolean deactivateProduct(UUID id) throws ProductNotFoundException;

    Boolean updateQuantityState(UUID productId, QuantityState quantityState) throws ProductNotFoundException;

    ProductDto getProductById(UUID productId) throws ProductNotFoundException;
}
