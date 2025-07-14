package ru.yandex.practicum.warehouse;

public class ProductInShoppingCartLowQuantityInWarehouse extends Exception {
    public ProductInShoppingCartLowQuantityInWarehouse(String message) {
        super(message);
    }
}
