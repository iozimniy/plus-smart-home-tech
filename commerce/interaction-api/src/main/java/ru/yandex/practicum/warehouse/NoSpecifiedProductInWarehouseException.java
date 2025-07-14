package ru.yandex.practicum.warehouse;

public class NoSpecifiedProductInWarehouseException extends Exception {
    public NoSpecifiedProductInWarehouseException(String message) {
        super(message);
    }
}
