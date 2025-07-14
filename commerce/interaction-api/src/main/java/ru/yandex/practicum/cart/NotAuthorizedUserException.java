package ru.yandex.practicum.cart;

public class NotAuthorizedUserException extends Exception {
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}
