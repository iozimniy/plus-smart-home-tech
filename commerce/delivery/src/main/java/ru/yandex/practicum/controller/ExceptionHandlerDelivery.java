package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.common.ErrorResponse;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.order.NoOrderFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerDelivery {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoDeliveryFoundException.class)
    public ErrorResponse handleMethodNoDeliveryFoundException(NoDeliveryFoundException e) {
        log.info("Send NoDeliveryFoundException with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Доставка не найдена")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoOrderFoundException.class)
    public ErrorResponse handleMethodNoOrderFoundException(NoOrderFoundException e) {
        log.info("Send NoOrderFoundException with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Заказ не найден")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }
}
