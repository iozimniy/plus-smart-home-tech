package ru.yandex.practicum.cart;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private UUID shoppingCartId;
    private Map<UUID, Integer> products;
}
