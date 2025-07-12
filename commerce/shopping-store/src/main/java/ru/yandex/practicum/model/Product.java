package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.yandex.practicum.products.ProductCategory;
import ru.yandex.practicum.products.ProductState;
import ru.yandex.practicum.products.QuantityState;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    @Column(name = "name")
    private String productName;
    @NotNull
    private String description;
    private String image;
    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_state")
    private QuantityState quantityState;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_state")
    private ProductState productState;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    private ProductCategory productCategory;
    private Double price;
}
