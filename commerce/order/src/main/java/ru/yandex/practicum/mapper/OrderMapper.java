package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.OrderProduct;
import ru.yandex.practicum.order.CreateNewOrderRequest;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.order.OrderState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public static OrderDto mapToDto(Order order) {
        Map<UUID, Integer> proructsMap = toMapOrderProducts(order.getProducts());

        return OrderDto.builder()
                .orderId(order.getId())
                .shoppingCartId(order.getCartId())
                .products(proructsMap)
                .paymentId(order.getPaymentId())
                .deliveryId(order.getDeliveryId())
                .state(order.getState())
                .deliveryWeight(order.getDeliveryWeight())
                .deliveryVolume(order.getDeliveryVolume())
                .fragile(order.getFragile())
                .totalPrice(order.getTotalPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .productPrice(order.getProductPrice())
                .build();
    }

    public static Map<UUID, Integer> toMapOrderProducts(List<OrderProduct> orderProducts) {
        return orderProducts.stream().collect(Collectors.toMap(
                OrderProduct::getProductId,
                OrderProduct::getQuantity
        ));
    }

    public static Order toOrder(CreateNewOrderRequest newOrderRequest) {
        return Order.builder()
                .cartId(newOrderRequest.getShoppingCart().getShoppingCartId())
                .state(OrderState.NEW)
                .build();
    }

    public static List<OrderProduct> mapToOrderProducts(Order order, Map<UUID, Integer> productMap) {
        ArrayList<OrderProduct> products = new ArrayList<>();

        for (Map.Entry<UUID, Integer> uuidIntegerEntry : productMap.entrySet()) {
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .productId(uuidIntegerEntry.getKey())
                    .quantity(uuidIntegerEntry.getValue())
                    .build();
            products.add(orderProduct);
        }

        return products;
    }
}
