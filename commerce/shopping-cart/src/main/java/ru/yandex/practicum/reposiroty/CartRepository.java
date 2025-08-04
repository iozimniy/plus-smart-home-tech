package ru.yandex.practicum.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Cart;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findByUsername(String username);

    Boolean existsByUsername(String username);
}
