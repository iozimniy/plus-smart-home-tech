package ru.yandex.practicum.warehouse;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssemblyProductsForOrderRequest {
    private Map<UUID, Integer> products;
    private UUID orderId;
}
