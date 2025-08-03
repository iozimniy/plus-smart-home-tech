package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrderDto> getOrders(@RequestParam String username) throws NotAuthorizedUserException {
        return service.getOrders(username);
    }

    @PutMapping
    public OrderDto createOrder(@RequestBody CreateNewOrderRequest newOrder)
            throws ProductInShoppingCartLowQuantityInWarehouse {
        return service.create(newOrder);
    }

    @PostMapping("/return")
    public OrderDto returnProducts(@RequestBody ProductReturnRequest returnRequest)
            throws NoOrderFoundException {
        return service.returnProducts(returnRequest);
    }

    @PostMapping("/payment")
    public OrderDto payment(UUID orderId) throws NoOrderFoundException {
        return service.payment(orderId);
    }
}
