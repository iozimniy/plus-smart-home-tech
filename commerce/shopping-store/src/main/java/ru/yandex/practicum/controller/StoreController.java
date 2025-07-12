package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.products.ProductCategory;
import ru.yandex.practicum.products.ProductDto;
import ru.yandex.practicum.products.QuantityState;
import ru.yandex.practicum.service.StoreService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class StoreController {

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

    @SneakyThrows
    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return service.updateProduct(productDto);
    }

    @SneakyThrows
    @PostMapping("/removeProductFromStore")
    public Boolean removeProduct(@RequestBody UUID productId) {
        return service.deactivateProduct(productId);
    }

    @PostMapping("quantityState")
    @SneakyThrows
    public Boolean updateQuantityState(@RequestParam UUID productId,
                                       @RequestParam QuantityState quantityState) {
        return service.updateQuantityState(productId, quantityState);
    }

    @SneakyThrows
    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) {
        return service.getProductById(productId);
    }
}
