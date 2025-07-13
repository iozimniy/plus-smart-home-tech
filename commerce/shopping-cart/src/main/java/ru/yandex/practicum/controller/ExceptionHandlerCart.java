package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.cart.NoProductsInShoppingCartException;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.common.ErrorResponse;
import ru.yandex.practicum.products.ProductNotFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerCart {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedUserException.class)
    public ErrorResponse handleMethodNotAuthorizedUserException(NotAuthorizedUserException e) {
        log.info("Send NotAuthorizedUserException with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Пользователь не найден или не доступен")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoProductsInShoppingCartException.class)
    public ErrorResponse handleMethodNoProductsInShoppingCartException(NoProductsInShoppingCartException e) {
        log.info("Send NoProductsInShoppingCartException with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Пользователь не найден или не доступен")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }
}
