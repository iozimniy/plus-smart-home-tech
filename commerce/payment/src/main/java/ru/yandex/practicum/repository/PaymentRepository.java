package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practicum.model.Payment;

import java.util.UUID;

public interface PaymentRepository extends CrudRepository<Payment, UUID> {

}
