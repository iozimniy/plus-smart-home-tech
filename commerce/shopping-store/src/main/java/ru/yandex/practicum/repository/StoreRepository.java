package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.products.ProductCategory;
import ru.yandex.practicum.products.ProductState;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByProductCategoryAndProductState(ProductCategory category,
                                                       ProductState productState,
                                                       Pageable pageable);

    Page<Product> findByProductState(ProductState productState, Pageable pageable);
}
