package ru.yandex.practicum.common.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.payment.PaymentDto;
import ru.yandex.practicum.products.ProductNotFoundException;

import java.util.UUID;

public interface PaymentOperations {

    @PostMapping
    PaymentDto pay(@RequestBody OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    @PostMapping("/totalCost")
    Double calculateTotalCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    @PostMapping("/refund")
    ResponseEntity<Void> refund(@RequestBody UUID paymentId) throws NoOrderFoundException;

    @PostMapping("/productCost")
    Double calculateProductsCost(OrderDto orderDto)
            throws ProductNotFoundException, NotEnoughInfoInOrderToCalculateException;

    @PostMapping("/failed")
    ResponseEntity<Void> failed(@RequestBody UUID paymentId) throws NoOrderFoundException;

}
