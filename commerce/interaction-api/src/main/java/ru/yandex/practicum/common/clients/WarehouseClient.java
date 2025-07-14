package ru.yandex.practicum.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.common.interfaces.WarehouseOperations;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient extends WarehouseOperations {

}
