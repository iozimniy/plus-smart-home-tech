package ru.yandex.practicum.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.common.interfaces.StoreOperations;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface StoreClient extends StoreOperations {

}
