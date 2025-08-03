package ru.yandex.practicum.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.common.interfaces.DeliveryOperations;

@FeignClient(name = "delivery", path = "/api/v1/delivery")
public interface DeliveryClient extends DeliveryOperations {
}
