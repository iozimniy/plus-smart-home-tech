package ru.yandex.practicum.service;

import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.warehouse.*;

public interface WarehouseService {
    void putProduct(NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException;

    BookedProductsDto checkProducts(CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse;

    void addQuantity(AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException;

    AddressDto getAddress();
}
