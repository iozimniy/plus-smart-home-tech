package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.common.clients.OrderClient;
import ru.yandex.practicum.common.clients.WarehouseClient;
import ru.yandex.practicum.delivery.DeliveryDto;
import ru.yandex.practicum.delivery.DeliveryState;
import ru.yandex.practicum.delivery.NoDeliveryFoundException;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.order.NoOrderFoundException;
import ru.yandex.practicum.order.OrderDto;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.warehouse.AddressDto;
import ru.yandex.practicum.warehouse.ShippedToDeliveryRequest;

import java.util.UUID;

import static ru.yandex.practicum.mapper.DeliveryMapper.mapToDelivery;
import static ru.yandex.practicum.mapper.DeliveryMapper.mapToDeliveryDto;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository repository;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;
    private static final Double BASE_DELIVERY_COST = 5.0;
    private static final Integer ADDRESS_1_RATIO = 1;
    private static final Integer ADDRESS_2_RATIO = 2;
    private static final Double FRAGILE_RATIO = 0.2;
    private static final Double WEIGHT_RATIO = 0.3;
    private static final Double VOLUME_RATIO = 0.2;
    private static final Double STREET_RATIO = 0.2;

    @Override
    @Transactional
    public DeliveryDto create(DeliveryDto deliveryDto) {
        log.info("Request for create delivery {}", deliveryDto);

        deliveryDto.setDeliveryState(DeliveryState.CREATED);



        Delivery delivery = repository.save(mapToDelivery(deliveryDto));

        return mapToDeliveryDto(delivery);
    }

    @Override
    public void successfulDelivery(UUID deliveryId) throws NoDeliveryFoundException, NoOrderFoundException {
        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow(
                        () -> new NoDeliveryFoundException("Доставка не найдена")
                );

        delivery.setState(DeliveryState.DELIVERED);

        repository.save(delivery);

        orderClient.delivery(delivery.getOrderId());
    }

    @Override
    public void productsPicked(UUID deliveryId) throws NoDeliveryFoundException, NoOrderFoundException {
        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow(
                        () -> new NoDeliveryFoundException("Доставка не найдена")
                );

        ShippedToDeliveryRequest shippedToDeliveryRequest = ShippedToDeliveryRequest.builder()
                        .deliveryId(delivery.getId())
                                .orderId(delivery.getOrderId())
                                        .build();

        warehouseClient.sentProducts(shippedToDeliveryRequest);

        delivery.setState(DeliveryState.IN_PROGRESS);
        repository.save(delivery);

        orderClient.assembly(delivery.getOrderId());
    }

    @Override
    public void failDelivery(UUID deliveryId) throws NoDeliveryFoundException, NoOrderFoundException {
        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow(
                        () -> new NoDeliveryFoundException("Доставка не найдена")
                );

        delivery.setState(DeliveryState.FAILED);
        repository.save(delivery);

        orderClient.failDelivery(delivery.getOrderId());
    }

    @Override
    public Double calculateDeliveryCost(OrderDto orderDto) throws NoDeliveryFoundException {
        Double totalDeliveryCost = BASE_DELIVERY_COST;

        Delivery delivery = repository.findById(orderDto.getDeliveryId())
                .orElseThrow(
                        () -> new NoDeliveryFoundException("Не найдена доставка для расчёта")
                );

        AddressDto warehouseAddress = warehouseClient.getAddress();

        if (warehouseAddress.getCity().equals("ADDRESS_1")) {
            totalDeliveryCost = totalDeliveryCost + (totalDeliveryCost * ADDRESS_1_RATIO);
        } else if (warehouseAddress.getCity().equals("ADDRESS_2")) {
            totalDeliveryCost = totalDeliveryCost + (totalDeliveryCost * ADDRESS_2_RATIO);
        }

        if (orderDto.getFragile()) {
            totalDeliveryCost = totalDeliveryCost + (totalDeliveryCost * FRAGILE_RATIO);
        }

        totalDeliveryCost = totalDeliveryCost + (orderDto.getDeliveryWeight() * WEIGHT_RATIO);
        totalDeliveryCost = totalDeliveryCost + (orderDto.getDeliveryVolume() * VOLUME_RATIO);

        if (!warehouseAddress.getCountry().equals(delivery.getToAddress().getCountry()) &&
        !warehouseAddress.getCity().equals(delivery.getToAddress().getCity()) &&
        !warehouseAddress.getStreet().equals(delivery.getToAddress().getStreet())) {
            totalDeliveryCost = totalDeliveryCost + (totalDeliveryCost * STREET_RATIO);
        }

        return totalDeliveryCost;
    }


}
