package ru.yandex.practicum.order;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReturnRequest {
    private UUID orderId;
    private Map<UUID, Integer> products;
}
