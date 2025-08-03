package ru.yandex.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.common.clients.OrderClient;
import ru.yandex.practicum.common.clients.WarehouseClient;

@SpringBootApplication
@EnableFeignClients(clients = {OrderClient.class, WarehouseClient.class})
public class Delivery {
    public static void main(String[] args) {
        SpringApplication.run(Delivery.class, args);
    }
}