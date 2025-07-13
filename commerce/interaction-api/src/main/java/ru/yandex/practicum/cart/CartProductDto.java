package ru.yandex.practicum.cart;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductDto {
    private Map<UUID, Integer> products;
}
