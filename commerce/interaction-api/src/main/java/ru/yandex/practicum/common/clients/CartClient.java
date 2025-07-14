package ru.yandex.practicum.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.common.interfaces.CartOperations;

@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart")
public interface CartClient extends CartOperations {

}
