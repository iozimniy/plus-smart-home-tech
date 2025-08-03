package ru.yandex.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.common.clients.CartClient;
import ru.yandex.practicum.common.clients.DeliveryClient;
import ru.yandex.practicum.common.clients.PaymentClient;
import ru.yandex.practicum.common.clients.WarehouseClient;

@SpringBootApplication
@EnableFeignClients(clients = {WarehouseClient.class, CartClient.class, PaymentClient.class, DeliveryClient.class})
public class Order {
    public static void main(String[] args) {
        SpringApplication.run(Order.class, args);
    }
}