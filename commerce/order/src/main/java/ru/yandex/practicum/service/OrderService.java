package ru.yandex.practicum.service;

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

public interface OrderService {
    List<OrderDto> getOrders(String username) throws NotAuthorizedUserException;

    OrderDto create(CreateNewOrderRequest newOrder) throws ProductInShoppingCartLowQuantityInWarehouse;

    OrderDto returnProducts(ProductReturnRequest returnRequest) throws NoOrderFoundException;

    OrderDto payment(UUID orderId) throws NoOrderFoundException;

    OrderDto failedPayment(UUID orderId) throws NoOrderFoundException;

    OrderDto delivery(UUID orderId) throws NoOrderFoundException;

    OrderDto failDelivery(UUID orderId) throws NoOrderFoundException;

    OrderDto complete(UUID orderId) throws NoOrderFoundException;

    OrderDto calculateTotal(UUID orderId) throws NoOrderFoundException, NotEnoughInfoInOrderToCalculateException;

    OrderDto calculateDelivery(UUID orderId) throws NoOrderFoundException, NoDeliveryFoundException;

    OrderDto assembly(UUID orderId) throws NoOrderFoundException;

    OrderDto failAssembly(UUID orderId) throws NoOrderFoundException;
}
