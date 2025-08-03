package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.common.clients.CartClient;
import ru.yandex.practicum.common.clients.WarehouseClient;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.OrderProduct;
import ru.yandex.practicum.order.*;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.warehouse.BookedProductsDto;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.List;

import static ru.yandex.practicum.mapper.OrderMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final CartClient cartClient;
    private final WarehouseClient warehouseClient;

    @Override
    public List<OrderDto> getOrders(String username) throws NotAuthorizedUserException {
        log.info("Request for receive orders with username {}", username);
        var cart = cartClient.getCart(username);
        return repository.findByCartId(cart.getShoppingCartId()).stream().map(order -> mapToDto(order)).toList();
    }

    //пока не дописан
    @Override
    @Transactional
    public OrderDto create(CreateNewOrderRequest newOrder) {
        log.info("Request for create new order {}", newOrder);

        Order order = repository.save(toOrder(newOrder));
        List<OrderProduct> orderProducts = mapToOrderProducts(order, newOrder.getShoppingCart().getProducts());

        order.setProducts(orderProducts);

        //TODO: понять, когда будет бронирование
//        AssemblyProductsForOrderRequest assemblyProductsForOrderRequest =
//                AssemblyProductsForOrderRequest.builder()
//                        .orderId(order.getId())
//                        .products(newOrder.getShoppingCart().getProducts())
//                        .build();
//
//        BookedProductsDto orderInfo = warehouseClient.assembly(assemblyProductsForOrderRequest);
//
//        order.setDeliveryWeight(orderInfo.getDeliveryWeight());
//        order.setDeliveryVolume(orderInfo.getDeliveryVolume());
//        order.setFragile(orderInfo.getFragile());

        repository.save(order);

        return mapToDto(order);
    }

    @Override
    public OrderDto returnProducts(ProductReturnRequest returnRequest) throws NoOrderFoundException {
        if (!repository.existById(returnRequest.getOrderId())) {
            throw new NoOrderFoundException("Заказ для возврата не найден");
        }

        warehouseClient.returnProducts(returnRequest.getProducts());

        //TODO: разобраться, когда менять статус заказа на возврат продуктов

        Order order = repository.findById(returnRequest.getOrderId()).get();
        order.setState(OrderState.PRODUCT_RETURNED);
        repository.save(order);

        return mapToDto(order);
    }

}
