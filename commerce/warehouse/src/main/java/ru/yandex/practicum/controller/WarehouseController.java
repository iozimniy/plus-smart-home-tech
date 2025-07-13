package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.AddressDto;
import ru.yandex.practicum.warehouse.BookedProductsDto;
import ru.yandex.practicum.warehouse.NewProductInWarehouseRequest;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService service;

    @PutMapping
    @SneakyThrows
    public ResponseEntity<Void> putProduct(@RequestBody NewProductInWarehouseRequest request) {
        service.putProduct(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    @SneakyThrows
    public BookedProductsDto checkProducts(@RequestBody CartDto cartDto) {
        return service.checkProducts(cartDto);
    }

    @PostMapping("/add")
    @SneakyThrows
    public ResponseEntity<Void> addQuantity(@RequestBody AddProductToWarehouseRequest request) {
        service.addQuantity(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        return service.getAddress();
    }
}
