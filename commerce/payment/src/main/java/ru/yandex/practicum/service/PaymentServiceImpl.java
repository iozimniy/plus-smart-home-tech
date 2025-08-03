package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.common.clients.OrderClient;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.payment.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.payment.PaymentDto;
import ru.yandex.practicum.payment.PaymentState;
import ru.yandex.practicum.repository.PaymentRepository;

import java.util.UUID;

import static ru.yandex.practicum.mapper.PaymentMapper.mapToDto;
import static ru.yandex.practicum.mapper.PaymentMapper.mapToPayment;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final OrderClient orderClient;

    @Override
    @Transactional
    public PaymentDto pay(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        //TODO: после реализации остальных методов проверить корректность расчётов

        if (orderDto.getDeliveryPrice() == null || orderDto.getTotalPrice() == null
        || orderDto.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно информации в заказе для расчёта");
        }

        Double fee = orderDto.getProductPrice() * 0.1;
        Payment payment = repository.save(mapToPayment(orderDto, fee));

        return mapToDto(payment);
    }

    @Override
    public Double calculateTotalCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        if (orderDto.getDeliveryPrice() == null || orderDto.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно информации в заказе для расчёта");
        }

        Double fee = orderDto.getProductPrice() * 0.1;

        return orderDto.getProductPrice() + orderDto.getDeliveryPrice() + fee;
    }

    @Override
    public void refund(UUID paymentId) throws NoOrderFoundException {
        Payment payment = repository.findById(paymentId).orElseThrow(
                () -> new NoOrderFoundException("Заказ не найден")
        );

        payment.setState(PaymentState.SUCCESS);
        repository.save(payment);

        orderClient.payment(payment.getOrderId());
    }

    @Override
    public Double calculateProductsCost(OrderDto orderDto) {
        return 0.0;
        //TODO: остановились тут!!!
    }
}
