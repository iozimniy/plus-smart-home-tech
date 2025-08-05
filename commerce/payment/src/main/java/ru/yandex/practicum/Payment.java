package ru.yandex.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.common.clients.OrderClient;
import ru.yandex.practicum.common.clients.StoreClient;

@SpringBootApplication
@EnableFeignClients(clients = {StoreClient.class, OrderClient.class})
public class Payment {
    public static void main(String[] args) {
        SpringApplication.run(Payment.class, args);
    }
}