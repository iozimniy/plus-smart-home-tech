package ru.yandex.practicum.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Order;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<ru.yandex.practicum.model.Order, UUID> {
    List<Order> findByCartId(UUID cartId);

    boolean existsById(@NonNull UUID id);
}
