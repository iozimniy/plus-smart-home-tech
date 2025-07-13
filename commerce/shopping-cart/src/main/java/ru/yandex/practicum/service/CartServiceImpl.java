package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.NoProductsInShoppingCartException;
import ru.yandex.practicum.cart.NotAuthorizedUserException;
import ru.yandex.practicum.common.clients.WarehouseClient;
import ru.yandex.practicum.model.Cart;
import ru.yandex.practicum.model.CartProduct;
import ru.yandex.practicum.reposiroty.CartRepository;
import ru.yandex.practicum.warehouse.ProductInShoppingCartLowQuantityInWarehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.yandex.practicum.mapper.CartMapper.toCartDto;
import static ru.yandex.practicum.mapper.CartMapper.toCartProduct;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final WarehouseClient warehouseClient;

    @Transactional
    @Override
    public CartDto addProduct(String username,
                              Map<UUID, Integer> products) throws NotAuthorizedUserException, ProductInShoppingCartLowQuantityInWarehouse {
        log.info("Request for add product from username: {} with products {}", username, products);
        log.debug("Request for add product from username: {} with products {}", username, products);
        checkCreateCart(username);

        Cart cart = repository.findByUsername(username);

        ArrayList<CartProduct> newProducts = new ArrayList<>();

        for (UUID uuid : products.keySet()) {
            newProducts.add(toCartProduct(cart, uuid, products.get(uuid)));
        }

        if (cart.getProducts() == null) {
            cart.setProducts(newProducts);
        } else {
            cart.getProducts().addAll(newProducts);
        }

        checkProductsQuantity(toCartDto(cart));

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
    @Transactional
    public CartDto removeProducts(String username, List<UUID> products) throws NotAuthorizedUserException,
            NoProductsInShoppingCartException {
        log.info("Request for remove products from username {} with uuid {}", username, products);
        chekUser(username);

        Cart cart = repository.findByUsername(username);
        List<CartProduct> cartProducts = cart.getProducts();
        checkProducts(cartProducts, products);

        ArrayList<CartProduct> newCartProductList = new ArrayList<>();
        for (CartProduct cartProduct : cartProducts) {
            if (!products.contains(cartProduct.getProductId())) {
                newCartProductList.add(cartProduct);
            }
        }

        cart.setProducts(newCartProductList);
        return toCartDto(repository.save(cart));
    }

    @Override
    @Transactional
    public CartDto changeQuantity(String username, ChangeProductQuantityRequest request) throws NotAuthorizedUserException, NoProductsInShoppingCartException {
        log.info("Request for change quantity product with uuid {} from usename {}", request.getProductId(), username);
        chekUser(username);

        Cart cart = repository.findByUsername(username);
        List<CartProduct> cartProducts = cart.getProducts();
        checkProducts(cartProducts, List.of(request.getProductId()));

        ArrayList<CartProduct> newCardProductsList = new ArrayList<>();
        newCardProductsList.add(toCartProduct(cart, request.getProductId(), request.getNewQuantity()));

        for (CartProduct cartProduct : cartProducts) {
            if (!cartProduct.getProductId().equals(request.getProductId())) {
                newCardProductsList.add(cartProduct);
            }
        }

        cart.setProducts(newCardProductsList);
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

    //прочие методы
    private void checkProductsQuantity(CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse {
        warehouseClient.checkProducts(cartDto);
    }

}
