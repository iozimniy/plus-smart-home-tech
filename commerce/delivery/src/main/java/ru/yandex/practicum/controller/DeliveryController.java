package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.common.interfaces.DeliveryOperations;
import ru.yandex.practicum.delivery.DeliveryDto;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryOperations {

    private final DeliveryService service;

    @PutMapping
    public DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto) {
        return service.create(deliveryDto);
    }

    @PostMapping("/successful")
    public ResponseEntity<Void> successfulDelivery(@RequestBody UUID deliveryId)
            throws NoOrderFoundException, NoDeliveryFoundException {
        service.successfulDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/picked")
    public ResponseEntity<Void> productsPicked(@RequestBody UUID deliveryId)
            throws NoDeliveryFoundException, NoOrderFoundException {
        service.productsPicked(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/failed")
    public ResponseEntity<Void> failDelivery(@RequestBody UUID deliveryId)
            throws NoOrderFoundException, NoDeliveryFoundException {
        service.failDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cost")
    public Double calculateDeliveryCost(OrderDto orderDto) throws NoDeliveryFoundException {
        return service.calculateDeliveryCost(orderDto);
    }
}
