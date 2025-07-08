package ru.yandex.practicum.controller;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ErrorResponse {
    private Throwable cause;
    private StackTraceElement[] stackTrace;
    private HttpStatus httpStatus;
    private String userMessage;
    private String message;
    private Throwable[] suppressed;
    private String localizedMessage;
}
