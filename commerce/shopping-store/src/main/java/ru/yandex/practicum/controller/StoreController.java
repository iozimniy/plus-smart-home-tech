package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.common.clients.StoreClient;
import ru.yandex.practicum.common.interfaces.StoreOperations;
import ru.yandex.practicum.products.ProductCategory;
import ru.yandex.practicum.products.ProductDto;
import ru.yandex.practicum.products.ProductNotFoundException;
import ru.yandex.practicum.products.QuantityState;
import ru.yandex.practicum.service.StoreService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class StoreController implements StoreOperations {

    private final StoreService service;

    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam ProductCategory category,
                                        @RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer size,
                                        @RequestParam(required = false) String sort) {
        return service.getProducts(category, page, size, sort);
    }

    @PutMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return service.createProduct(productDto);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) throws ProductNotFoundException {
        return service.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    public Boolean removeProduct(@RequestBody UUID productId) throws ProductNotFoundException {
        return service.deactivateProduct(productId);
    }

    @PostMapping("/quantityState")
    public Boolean updateQuantityState(@RequestParam UUID productId,
                                       @RequestParam QuantityState quantityState) throws ProductNotFoundException {
        return service.updateQuantityState(productId, quantityState);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) throws ProductNotFoundException {
        return service.getProductById(productId);
    }
}
