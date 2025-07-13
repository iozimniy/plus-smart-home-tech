package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.NoProductsInShoppingCartException;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.common.clients.CartClient;
import ru.yandex.practicum.service.CartService;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class CartController implements CartClient {
    private final CartService service;

    @PutMapping
    public CartDto addProduct(@RequestParam String username,
                              @RequestBody Map<UUID, Integer> products)
            throws ProductInShoppingCartLowQuantityInWarehouse, NotAuthorizedUserException {
        return service.addProduct(username, products);
    }

    @GetMapping
    public CartDto getCart(@RequestParam String username) throws NotAuthorizedUserException {
        return service.getCart(username);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestParam String username) throws NotAuthorizedUserException {
        service.deleteCart(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public CartDto removeProducts(@RequestParam String username, @RequestBody List<UUID> products)
            throws NoProductsInShoppingCartException, NotAuthorizedUserException {
        return service.removeProducts(username, products);
    }

    @PostMapping("/change-quantity")
    public CartDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest request)
            throws NoProductsInShoppingCartException, NotAuthorizedUserException {
        return service.changeQuantity(username, request);
    }

}
