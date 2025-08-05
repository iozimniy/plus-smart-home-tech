package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.common.interfaces.PaymentOperations;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.payment.PaymentDto;
import ru.yandex.practicum.products.ProductNotFoundException;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentOperations {

    private final PaymentService service;

    @PostMapping
    public PaymentDto pay(@RequestBody OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        return service.pay(orderDto);
    }

    @PostMapping("/totalCost")
    public Double calculateTotalCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        return service.calculateTotalCost(orderDto);
    }

    @PostMapping("/refund")
    public ResponseEntity<Void> refund(@RequestBody UUID paymentId) throws NoOrderFoundException {
        service.refund(paymentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/productCost")
    public Double calculateProductsCost(OrderDto orderDto)
            throws ProductNotFoundException, NotEnoughInfoInOrderToCalculateException {
        return service.calculateProductsCost(orderDto);
    }

    @PostMapping("/failed")
    public ResponseEntity<Void> failed(@RequestBody UUID paymentId) throws NoOrderFoundException {
        service.refusePayment(paymentId);
        return ResponseEntity.ok().build();
    }
}
