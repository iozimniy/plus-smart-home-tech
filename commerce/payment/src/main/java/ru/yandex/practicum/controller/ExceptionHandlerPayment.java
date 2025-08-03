package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.common.ErrorResponse;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerPayment {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotEnoughInfoInOrderToCalculateException.class)
    public ErrorResponse handleMethodNotEnoughInfoInOrderToCalculateException
            (NotEnoughInfoInOrderToCalculateException e) {
        log.info("Send NotEnoughInfoInOrderToCalculateException with message {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(e.getStackTrace())
                .httpStatus(HttpStatus.NOT_FOUND)
                .userMessage("Недостаточно информации в заказе для расчёта")
                .message(e.getMessage())
                .suppressed(e.getSuppressed())
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }
}
