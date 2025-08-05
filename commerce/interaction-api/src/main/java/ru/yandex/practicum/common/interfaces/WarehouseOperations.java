package ru.yandex.practicum.common.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.warehouse.*;

import java.util.Map;
import java.util.UUID;

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

    @PostMapping("/return")
    ResponseEntity<Void> returnProducts(Map<UUID, Integer> returnProducts);

    @PostMapping("/assembly")
    BookedProductsDto assembly(@RequestBody AssemblyProductsForOrderRequest assemblyProductsForOrderRequest)
            throws ProductInShoppingCartLowQuantityInWarehouse;

    @PostMapping("/shipped")
    ResponseEntity<Void> sentProducts(@RequestBody ShippedToDeliveryRequest shippedToDeliveryRequest);
}
