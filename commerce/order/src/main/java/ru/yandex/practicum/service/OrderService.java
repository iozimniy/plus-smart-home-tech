package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.order.ProductReturnRequest;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrders(String username) throws NotAuthorizedUserException;

    OrderDto create(CreateNewOrderRequest newOrder);

    OrderDto returnProducts(ProductReturnRequest returnRequest) throws NoOrderFoundException;
}
