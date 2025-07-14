package ru.yandex.practicum.common.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.NoProductsInShoppingCartException;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartOperations {
    @PutMapping
    CartDto addProduct(@RequestParam String username,
                       @RequestBody Map<UUID, Integer> products)
            throws ProductInShoppingCartLowQuantityInWarehouse, NotAuthorizedUserException;

    @GetMapping
    CartDto getCart(@RequestParam String username) throws NotAuthorizedUserException;

    @DeleteMapping
    ResponseEntity<Void> deleteCart(@RequestParam String username) throws NotAuthorizedUserException;

    @PostMapping("/remove")
    CartDto removeProducts(@RequestParam String username, @RequestBody List<UUID> products)
            throws NoProductsInShoppingCartException, NotAuthorizedUserException;

    @PostMapping("/change-quantity")
    CartDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest request)
            throws NoProductsInShoppingCartException, NotAuthorizedUserException;
}
