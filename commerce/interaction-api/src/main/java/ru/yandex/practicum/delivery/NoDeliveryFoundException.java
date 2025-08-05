package ru.yandex.practicum.delivery;

public class NoDeliveryFoundException extends Exception {
    public NoDeliveryFoundException(String message) {
        super(message);
    }
}
