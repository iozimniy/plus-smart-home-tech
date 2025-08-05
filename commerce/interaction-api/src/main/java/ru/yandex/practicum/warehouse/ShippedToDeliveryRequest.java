package ru.yandex.practicum.warehouse;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippedToDeliveryRequest {
    private UUID orderId;
    private UUID deliveryId;
}
