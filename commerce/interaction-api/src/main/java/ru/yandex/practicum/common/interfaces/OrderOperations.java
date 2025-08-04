package ru.yandex.practicum.common.interfaces;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.List;
import java.util.UUID;

public interface OrderOperations {

    @GetMapping
    List<OrderDto> getOrders(@RequestParam String username) throws NotAuthorizedUserException;

    @PutMapping
    OrderDto createOrder(@RequestBody CreateNewOrderRequest newOrder)
            throws ProductInShoppingCartLowQuantityInWarehouse;

    @PostMapping("/return")
    OrderDto returnProducts(@RequestBody ProductReturnRequest returnRequest) throws NoOrderFoundException;

    @PostMapping("/payment")
    OrderDto payment(UUID orderId) throws NoOrderFoundException;

    @PostMapping("/failed")
    OrderDto failedPayment(@RequestBody UUID orderId) throws NoOrderFoundException;

    @PostMapping("/delivery")
    OrderDto delivery(@RequestBody UUID orderId) throws NoOrderFoundException;

    @PostMapping("/delivery/failed")
    OrderDto failDelivery(@RequestBody UUID orderId) throws NoOrderFoundException;

    @PostMapping("/completed")
    OrderDto complete(@RequestBody UUID orderId) throws NoOrderFoundException;

    @PostMapping("/calculate/total")
    OrderDto calculateTotal(@RequestBody UUID orderId)
            throws NoOrderFoundException, NotEnoughInfoInOrderToCalculateException;

    @PostMapping("calculate/delivery")
    OrderDto calculateDelivery(@RequestBody UUID orderId)
            throws NoOrderFoundException, NoDeliveryFoundException;

    @PostMapping("/assembly")
    OrderDto assembly(@RequestBody UUID orderId) throws NoOrderFoundException;

    @PostMapping("/assembly/failed")
    OrderDto failAssembly(@RequestBody UUID orderId) throws NoOrderFoundException;
}
