package ru.yandex.practicum.service;

import ru.yandex.practicum.delivery.DeliveryDto;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;

import java.util.UUID;

public interface DeliveryService {
    DeliveryDto create(DeliveryDto deliveryDto);

    void successfulDelivery(UUID deliveryId) throws NoDeliveryFoundException, NoOrderFoundException;

    void productsPicked(UUID deliveryId) throws NoDeliveryFoundException, NoOrderFoundException;

    void failDelivery(UUID deliveryId) throws NoDeliveryFoundException, NoOrderFoundException;

    Double calculateDeliveryCost(OrderDto orderDto) throws NoDeliveryFoundException;
}
