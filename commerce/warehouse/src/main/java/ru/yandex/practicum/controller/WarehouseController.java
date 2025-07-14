package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.common.clients.WarehouseClient;
import ru.yandex.practicum.common.interfaces.WarehouseOperations;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.*;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController implements WarehouseOperations {
    private final WarehouseService service;

    @PutMapping
    public ResponseEntity<Void> putProduct(@RequestBody NewProductInWarehouseRequest request)
            throws SpecifiedProductAlreadyInWarehouseException {
        service.putProduct(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    public BookedProductsDto checkProducts(@RequestBody CartDto cartDto)
            throws ProductInShoppingCartLowQuantityInWarehouse {
        return service.checkProducts(cartDto);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addQuantity(@RequestBody AddProductToWarehouseRequest request)
            throws NoSpecifiedProductInWarehouseException {
        service.addQuantity(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        return service.getAddress();
    }
}
