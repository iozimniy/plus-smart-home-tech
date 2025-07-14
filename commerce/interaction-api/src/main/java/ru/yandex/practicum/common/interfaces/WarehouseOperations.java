package ru.yandex.practicum.common.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.warehouse.*;

public interface WarehouseOperations {
    @PostMapping("/check")
    BookedProductsDto checkProducts(@RequestBody CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse;

    @PutMapping
    ResponseEntity<Void> putProduct(@RequestBody NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException;

    @PostMapping("/add")
    ResponseEntity<Void> addQuantity(@RequestBody AddProductToWarehouseRequest request)
            throws NoSpecifiedProductInWarehouseException;

    @GetMapping("/address")
    AddressDto getAddress();
}
