package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;
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
    public OrderDto payment(@RequestBody UUID orderId) throws NoOrderFoundException {
        return service.payment(orderId);
    }

    @PostMapping("/failed")
    public OrderDto failedPayment(@RequestBody UUID orderId) throws NoOrderFoundException {
        return service.failedPayment(orderId);
    }

    @PostMapping("/delivery")
    public OrderDto delivery(@RequestBody UUID orderId) throws NoOrderFoundException {
        return service.delivery(orderId);
    }

    @PostMapping("/delivery/failed")
    public OrderDto failDelivery(@RequestBody UUID orderId) throws NoOrderFoundException {
        return service.failDelivery(orderId);
    }

    @PostMapping("/completed")
    public OrderDto complete(@RequestBody UUID orderId) throws NoOrderFoundException {
        return service.complete(orderId);
    }

    @PostMapping("/calculate/total")
    public OrderDto calculateTotal(@RequestBody UUID orderId)
            throws NoOrderFoundException, NotEnoughInfoInOrderToCalculateException {
        return service.calculateTotal(orderId);
    }

    @PostMapping("calculate/delivery")
    public OrderDto calculateDelivery (@RequestBody UUID orderId)
            throws NoOrderFoundException, NoDeliveryFoundException {
        return service.calculateDelivery(orderId);
    }

    @PostMapping("/assembly")
    public OrderDto assembly(@RequestBody UUID orderId) throws NoOrderFoundException {
        return service.assembly(orderId);
    }

    @PostMapping("/assembly/failed")
    private OrderDto failAssembly(@RequestBody UUID orderId) throws NoOrderFoundException {
        return service.failAssembly(orderId);
    }
}
