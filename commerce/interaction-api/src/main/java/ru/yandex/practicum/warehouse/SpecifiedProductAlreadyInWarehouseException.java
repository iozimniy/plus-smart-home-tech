package ru.yandex.practicum.warehouse;

public class SpecifiedProductAlreadyInWarehouseException extends Exception {
    public SpecifiedProductAlreadyInWarehouseException(String message) {
        super(message);
    }
}
