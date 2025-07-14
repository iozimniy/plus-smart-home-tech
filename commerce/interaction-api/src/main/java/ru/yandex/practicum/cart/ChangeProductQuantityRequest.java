package ru.yandex.practicum.cart;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeProductQuantityRequest {
    private UUID productId;
    private Integer newQuantity;
}
