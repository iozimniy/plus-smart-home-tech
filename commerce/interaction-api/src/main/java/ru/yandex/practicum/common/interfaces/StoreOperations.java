package ru.yandex.practicum.common.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.products.ProductCategory;
import ru.yandex.practicum.products.ProductDto;
import ru.yandex.practicum.products.ProductNotFoundException;
import ru.yandex.practicum.products.QuantityState;

import java.util.UUID;

public interface StoreOperations {

    @GetMapping
    Page<ProductDto> getProducts(@RequestParam ProductCategory category,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size,
                                 @RequestParam(required = false) String sort);

    @PostMapping
    ProductDto updateProduct(@RequestBody ProductDto productDto) throws ProductNotFoundException;

    @PostMapping("/removeProductFromStore")
    Boolean removeProduct(@RequestBody UUID productId) throws ProductNotFoundException;

    @PostMapping("/quantityState")
    Boolean updateQuantityState(@RequestParam UUID productId,
                                @RequestParam QuantityState quantityState) throws ProductNotFoundException;

    @GetMapping("/{productId}")
    ProductDto getProductById(@PathVariable UUID productId) throws ProductNotFoundException;
}
