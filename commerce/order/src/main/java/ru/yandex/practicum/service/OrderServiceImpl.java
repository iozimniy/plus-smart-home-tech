package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.common.clients.CartClient;
import ru.yandex.practicum.common.clients.DeliveryClient;
import ru.yandex.practicum.common.clients.PaymentClient;
import ru.yandex.practicum.common.clients.WarehouseClient;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.OrderProduct;
import ru.yandex.practicum.order.*;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.warehouse.BookedProductsDto;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.List;
import java.util.UUID;

import static ru.yandex.practicum.mapper.OrderMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final CartClient cartClient;
    private final WarehouseClient warehouseClient;
    private final PaymentClient paymentClient;
    private final DeliveryClient deliveryClient;

    @Override
    public List<OrderDto> getOrders(String username) throws NotAuthorizedUserException {
        log.info("Request for receive orders with username {}", username);
        var cart = cartClient.getCart(username);
        return repository.findByCartId(cart.getShoppingCartId()).stream().map(OrderMapper::mapToDto).toList();
    }

    //пока не дописан
    @Override
    @Transactional
    public OrderDto create(CreateNewOrderRequest newOrder) throws ProductInShoppingCartLowQuantityInWarehouse {
        log.info("Request for create new order {}", newOrder);

        Order order = repository.save(toOrder(newOrder));
        List<OrderProduct> orderProducts = mapToOrderProducts(order, newOrder.getShoppingCart().getProducts());

        order.setProducts(orderProducts);

        AssemblyProductsForOrderRequest assemblyProductsForOrderRequest =
                AssemblyProductsForOrderRequest.builder()
                        .orderId(order.getId())
                        .products(newOrder.getShoppingCart().getProducts())
                        .build();

        BookedProductsDto orderInfo = warehouseClient.assembly(assemblyProductsForOrderRequest);

        order.setDeliveryWeight(orderInfo.getDeliveryWeight());
        order.setDeliveryVolume(orderInfo.getDeliveryVolume());
        order.setFragile(orderInfo.getFragile());

        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto returnProducts(ProductReturnRequest returnRequest) throws NoOrderFoundException {
        log.info("Request for return products {}", returnRequest);

        if (!repository.existsById(returnRequest.getOrderId())) {
            throw new NoOrderFoundException("Заказ для возврата не найден");
        }

        warehouseClient.returnProducts(returnRequest.getProducts());

        Order order = repository.findById(returnRequest.getOrderId()).get();
        order.setState(OrderState.PRODUCT_RETURNED);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto payment(UUID orderId) throws NoOrderFoundException {
        log.info("Request for success payment of order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        order.setState(OrderState.PAID);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto failedPayment(UUID orderId) throws NoOrderFoundException {
        log.info("Request for failed payment of order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        order.setState(OrderState.PAYMENT_FAILED);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto delivery(UUID orderId) throws NoOrderFoundException {
        log.info("Request for success delivery of order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        order.setState(OrderState.DELIVERED);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto failDelivery(UUID orderId) throws NoOrderFoundException {
        log.info("Request for delivery of order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        order.setState(OrderState.DELIVERY_FAILED);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto complete(UUID orderId) throws NoOrderFoundException {
        log.info("Request for complete order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        order.setState(OrderState.COMPLETED);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateTotal(UUID orderId) throws NoOrderFoundException,
            NotEnoughInfoInOrderToCalculateException {
        log.info("Request for calculate total cost for order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        Double total = paymentClient.calculateTotalCost(mapToDto(order));
        order.setTotalPrice(total);
        order.setState(OrderState.ON_PAYMENT);

        repository.save(order);
        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateDelivery(UUID orderId) throws NoOrderFoundException, NoDeliveryFoundException {
        log.info("Request for calculate delivery for order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        Double deliveryCost = deliveryClient.calculateDeliveryCost(mapToDto(order));
        order.setDeliveryPrice(deliveryCost);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto assembly(UUID orderId) throws NoOrderFoundException {
        log.info("Request for assembly for order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        order.setState(OrderState.ASSEMBLED);
        repository.save(order);

        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto failAssembly(UUID orderId) throws NoOrderFoundException {
        log.info("Request for fail assembly of order {}", orderId);

        Order order = repository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Не найден заказ")
        );

        order.setState(OrderState.ASSEMBLY_FAILED);
        repository.save(order);

        return mapToDto(order);
    }


}
