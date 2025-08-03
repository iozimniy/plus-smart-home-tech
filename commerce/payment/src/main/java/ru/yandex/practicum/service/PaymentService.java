package ru.yandex.practicum.service;

import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.payment.PaymentDto;

import java.util.UUID;

public interface PaymentService {
    PaymentDto pay(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    Double calculateTotalCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException;

    void refund(UUID paymentId) throws NoOrderFoundException;

    Double calculateProductsCost(OrderDto orderDto);
}
