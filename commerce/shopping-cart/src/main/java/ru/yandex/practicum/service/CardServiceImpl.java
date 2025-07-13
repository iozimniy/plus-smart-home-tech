package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.*;
import ru.yandex.practicum.model.Cart;
import ru.yandex.practicum.model.CartProduct;
import ru.yandex.practicum.reposiroty.CartRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.yandex.practicum.mapper.CartMapper.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CardServiceImpl implements CartService {

    private final CartRepository repository;

    @Transactional
    @Override
    public CartDto addProduct(String username,
                                           Map<UUID, Integer> products) throws NotAuthorizedUserException {
        log.info("Request for add product from username: {} with products {}", username, products);
        log.debug("Request for add product from username: {} with products {}", username, products);
        checkCreateCart(username);


        Cart cart = repository.findByUsername(username);
        List<CartProduct> cartProducts = cart.getProducts();

        for (UUID uuid : products.keySet()) {
            cartProducts.add(toCartProduct(cart, uuid, products.get(uuid)));
        }

        cart.setProducts(cartProducts);

        return toCartDto(repository.save(cart));
    }

    @Override
    public CartDto getCart(String username) throws NotAuthorizedUserException {
        log.info("Request for getting cart from username: {}", username);
        chekUser(username);
        return toCartDto(repository.findByUsername(username));
    }

    @Override
    @Transactional
    public void deleteCart(String username) throws NotAuthorizedUserException {
        log.info("Request for delete cart from username: {}", username);
        chekUser(username);

        Cart cart = repository.findByUsername(username);
        cart.setActive(false);
        repository.save(cart);
    }

    @Override
    public CartDto removeProducts(String username, List<UUID> products) throws NotAuthorizedUserException,
            NoProductsInShoppingCartException {
        log.info("Request for remove products from username {} with uuid {}", username, products);
        chekUser(username);

        Cart cart = repository.findByUsername(username);
        List<CartProduct> cartProducts = cart.getProducts();

        checkProducts(cartProducts, products);
        cartProducts.stream().filter(cartProduct -> !products.contains(cartProduct.getProductId()))
                .collect(Collectors.toList());
        cart.setProducts(cartProducts);
        return toCartDto(repository.save(cart));
    }

    @Override
    public CartDto changeQuantity(String username, ChangeProductQuantityRequest request) throws NotAuthorizedUserException, NoProductsInShoppingCartException {
        log.info("Request for change quantity product with uuid {} from usename {}", request.getProductId(), username);
        chekUser(username);

        Cart cart = repository.findByUsername(username);
        List<CartProduct> cartProducts = cart.getProducts();
        checkProducts(cartProducts, List.of(request.getProductId()));

        cartProducts.remove(toCartProduct(cart, request.getProductId(), request.getNewQuantity()));
        cartProducts.add(toCartProduct(cart, request.getProductId(), request.getNewQuantity()));
        cart.setProducts(cartProducts);
        return toCartDto(repository.save(cart));
    }

    //вспомогательные методы

    private void chekUser(String username) throws NotAuthorizedUserException {
        if (!repository.existsByUsername(username)) {
            throw new NotAuthorizedUserException("Пользователь не доступен или не найден");
        }
    }

    private void checkCreateCart(String username) {
       if (!repository.existsByUsername(username)) {
           Cart newCart = Cart.builder().username(username).build();
           repository.save(newCart);
       }
    }

    private void checkProducts(List<CartProduct> cartProducts, List<UUID> list)
            throws NoProductsInShoppingCartException {
        List<UUID> uuids = cartProducts
                .stream().map(cartProduct -> cartProduct.getProductId()).toList();

        for (UUID uuid : list) {
            if (!uuids.contains(uuid)) {
                throw new NoProductsInShoppingCartException("Товара нет в корзине");
            }
        }
    }

}
