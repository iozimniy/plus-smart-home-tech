package ru.yandex.practicum.order;

import lombok.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.warehouse.AddressDto;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewOrderRequest {
    private CartDto shoppingCart;
    private AddressDto deliveryAddress;
}
