package ru.yandex.practicum.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.warehouse.*;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {

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
