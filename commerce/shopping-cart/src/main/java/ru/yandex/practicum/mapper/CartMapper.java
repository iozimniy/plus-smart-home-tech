package ru.yandex.practicum.mapper;

import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.model.Cart;
import ru.yandex.practicum.model.CartProduct;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartDto toCartDto(Cart cart) {

        Map<UUID, Integer> proructsMap = toListCartProductDto(cart.getProducts());

        return CartDto.builder()
                .shoppingCartId(cart.getId())
                .products(proructsMap)
                .build();
    }

    public static CartProduct toCartProduct(Cart cart, UUID productId, Integer quantity) {
        return CartProduct.builder()
                .cart(cart)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    public static Map<UUID, Integer> toListCartProductDto(List<CartProduct> products) {
        return products.stream().collect(Collectors.toMap(
                s -> s.getProductId(),
                s -> s.getQuantity()
        ));
    }
}
