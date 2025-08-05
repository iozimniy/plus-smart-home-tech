package ru.yandex.practicum.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.common.interfaces.PaymentOperations;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient extends PaymentOperations {
}
