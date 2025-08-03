package ru.yandex.practicum.common.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.delivery.DeliveryDto;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;

import java.util.UUID;

public interface DeliveryOperations {

    @PutMapping
    DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/successful")
    ResponseEntity<Void> successfulDelivery(@RequestBody UUID deliveryId)
            throws NoOrderFoundException, NoDeliveryFoundException;

    @PostMapping("/picked")
    ResponseEntity<Void> productsPicked(@RequestBody UUID deliveryId)
            throws NoDeliveryFoundException, NoOrderFoundException;

    @PostMapping("/failed")
    ResponseEntity<Void> failDelivery(@RequestBody UUID deliveryId)
            throws NoOrderFoundException, NoDeliveryFoundException;

    @PostMapping("/cost")
    Double calculateDeliveryCost(OrderDto orderDto) throws NoDeliveryFoundException;
}
