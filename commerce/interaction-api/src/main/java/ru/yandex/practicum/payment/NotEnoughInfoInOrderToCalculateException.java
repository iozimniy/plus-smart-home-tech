package ru.yandex.practicum.payment;

public class NotEnoughInfoInOrderToCalculateException extends Exception {
    public NotEnoughInfoInOrderToCalculateException(String message) {
        super(message);
    }
}
