package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.payment.PaymentDto;
import ru.yandex.practicum.payment.PaymentState;

@Component
public class PaymentMapper {
    public static Payment mapToPayment(OrderDto orderDto, Double fee) {

        return Payment.builder()
                .orderId(orderDto.getOrderId())
                .total(orderDto.getTotalPrice())
                .productsTotal(orderDto.getProductPrice())
                .deliveryTotal(orderDto.getDeliveryPrice())
                .state(PaymentState.PENDING)
                .feeTotal(fee)
                .build();
    }

    public static PaymentDto mapToDto(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getId())
                .totalPayment(payment.getTotal())
                .deliveryTotal(payment.getDeliveryTotal())
                .feeTotal(payment.getFeeTotal())
                .build();
    }
}