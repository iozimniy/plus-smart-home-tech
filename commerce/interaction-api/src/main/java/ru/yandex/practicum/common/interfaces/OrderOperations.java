package ru.yandex.practicum.common.interfaces;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderOperations {

    @GetMapping
    List<OrderDto> getOrders(@RequestParam String username) throws NotAuthorizedUserException;

    @PutMapping
    OrderDto createOrder(@RequestBody CreateNewOrderRequest newOrder);

    @PostMapping("/return")
    OrderDto returnProducts(@RequestBody ProductReturnRequest returnRequest);

    @PostMapping("/payment")
    OrderDto payment(UUID orderId) throws NoOrderFoundException;
}
