package ru.yandex.practicum.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Product, UUID> {
    boolean existsById(@NonNull UUID id);

    List<Product> findByIdIn(List<UUID> uuids);
}
