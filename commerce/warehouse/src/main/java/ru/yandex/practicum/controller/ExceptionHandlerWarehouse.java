package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.common.ErrorResponse;
import ru.yandex.practicum.warehouse.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.warehouse.SpecifiedProductAlreadyInWarehouseException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerWarehouse {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SpecifiedProductAlreadyInWarehouseException.class)
    public ErrorResponse handleMethodSpecifiedProductAlreadyInWarehouseException(
            SpecifiedProductAlreadyInWarehouseException e) {
        log.info("Send SpecifiedProductAlreadyInWarehouseException with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Tовар с таким описанием уже зарегистрирован на складе")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    public ErrorResponse handleMethodProductInShoppingCartLowQuantityInWarehouse(
            ProductInShoppingCartLowQuantityInWarehouse e) {
        log.info("Send ProductInShoppingCartLowQuantityInWarehouse with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Товар из корзины не находится в требуемом количестве на складе")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSpecifiedProductInWarehouseException.class)
    public ErrorResponse handleMethodNoSpecifiedProductInWarehouseException(
            NoSpecifiedProductInWarehouseException e) {
        log.info("Send NoSpecifiedProductInWarehouseException with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Нет информации о товаре на складе")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }
}
