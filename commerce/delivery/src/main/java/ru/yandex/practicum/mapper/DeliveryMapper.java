package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.delivery.DeliveryDto;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.warehouse.AddressDto;

@Component
public class DeliveryMapper {

    public static Delivery mapToDelivery(DeliveryDto deliveryDto) {
        return Delivery.builder()
                .fromAddress(mapToAddress(deliveryDto.getFromAddress()))
                .toAddress(mapToAddress(deliveryDto.getToAddress()))
                .orderId(deliveryDto.getOrderId())
                .state(deliveryDto.getDeliveryState())
                .build();

    }

    public static Address mapToAddress(AddressDto addressDto) {
        return Address.builder()
                .country(addressDto.getCountry())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .house(addressDto.getHouse())
                .flat(addressDto.getFlat())
                .build();
    }

    public static AddressDto mapToAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }

    public static DeliveryDto mapToDeliveryDto(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getId())
                .fromAddress(mapToAddressDto(delivery.getFromAddress()))
                .toAddress(mapToAddressDto(delivery.getToAddress()))
                .orderId(delivery.getOrderId())
                .deliveryState(delivery.getState())
                .build();
    }
}
