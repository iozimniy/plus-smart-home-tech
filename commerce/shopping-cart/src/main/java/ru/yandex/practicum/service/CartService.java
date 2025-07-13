package ru.yandex.practicum.service;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.cart.*;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {
    CartDto addProduct(String username, Map<UUID, Integer> products) throws NotAuthorizedUserException, ProductInShoppingCartLowQuantityInWarehouse;

    CartDto getCart(String username) throws NotAuthorizedUserException;

    void deleteCart(String username) throws NotAuthorizedUserException;

    CartDto removeProducts(String username, List<UUID> products) throws NotAuthorizedUserException, NoProductsInShoppingCartException;

    CartDto changeQuantity(String username, ChangeProductQuantityRequest request) throws NotAuthorizedUserException, NoProductsInShoppingCartException;
}
