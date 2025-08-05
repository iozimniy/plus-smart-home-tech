package ru.yandex.practicum.service;

import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.warehouse.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {
    void putProduct(NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException;

    BookedProductsDto checkProducts(CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse;

    void addQuantity(AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException;

    AddressDto getAddress();

    void sentProducts(ShippedToDeliveryRequest shippedToDeliveryRequest);

    void returnProducts(Map<UUID, Integer> returnProducts);

    BookedProductsDto assembly(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest) throws ProductInShoppingCartLowQuantityInWarehouse;
}
