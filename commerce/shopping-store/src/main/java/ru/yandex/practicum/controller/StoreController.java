package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.products.ProductCategory;
import ru.yandex.practicum.products.ProductDto;
import ru.yandex.practicum.products.QuantityState;
import ru.yandex.practicum.service.StoreService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService service;

    @GetMapping
    public Collection<ProductDto> getProducts(@RequestParam ProductCategory category,
                                              @RequestParam Integer page,
                                              @RequestParam Integer size,
                                              @RequestParam String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return service.getProducts(category, pageable);
    }

    @PutMapping
    public ProductDto createProduct(@RequestParam ProductDto productDto) {
        return service.createProduct(productDto);
    }

    @SneakyThrows
    @PostMapping
    public ProductDto updateProduct(@RequestParam ProductDto productDto) {
        return service.updateProduct(productDto);
    }

    @SneakyThrows
    @PostMapping("/removeProductFromStore")
    public Boolean removeProduct(@RequestParam UUID productId) {
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
