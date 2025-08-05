package ru.yandex.practicum.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Product;

import java.util.List;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Product, UUID> {
    boolean existsById(@NonNull UUID id);

    List<Product> findByIdIn(List<UUID> uuids);
}
