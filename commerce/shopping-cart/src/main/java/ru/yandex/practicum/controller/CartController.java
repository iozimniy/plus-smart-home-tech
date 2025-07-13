package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @SneakyThrows
    @PutMapping
    public CartDto addProduct(@RequestParam String username,
                              @RequestBody Map<UUID, Integer> products) {
        return service.addProduct(username, products);
    }

    @SneakyThrows
    @GetMapping
    public CartDto getCart(@RequestParam String username) {
        return service.getCart(username);
    }

    @DeleteMapping
    @SneakyThrows
    public ResponseEntity<Void> deleteCart(@RequestParam String username) {
        service.deleteCart(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    @SneakyThrows
    public CartDto removeProducts(@RequestParam String username, @RequestBody List<UUID> products) {
        return service.removeProducts(username, products);
    }

    @PostMapping("/change-quantity")
    @SneakyThrows
    public CartDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest request) {
        return service.changeQuantity(username, request);
    }

}
