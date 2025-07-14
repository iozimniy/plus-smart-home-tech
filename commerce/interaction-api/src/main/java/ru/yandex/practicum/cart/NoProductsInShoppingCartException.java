package ru.yandex.practicum.cart;

public class NoProductsInShoppingCartException extends Exception {
    public NoProductsInShoppingCartException(String message) {
        super(message);
    }
}
