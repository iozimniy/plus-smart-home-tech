package ru.yandex.practicum.order;

public class NoOrderFoundException extends Exception {
    public NoOrderFoundException(String message) {
        super(message);
    }
}
