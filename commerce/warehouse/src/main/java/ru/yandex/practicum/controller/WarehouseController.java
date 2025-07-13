package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.common.clients.WarehouseClient;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.warehouse.*;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
@Slf4j
public class WarehouseController implements WarehouseClient {
    private final WarehouseService service;

    @PutMapping
    @SneakyThrows
    public ResponseEntity<Void> putProduct(@RequestBody NewProductInWarehouseRequest request) {
        service.putProduct(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    public BookedProductsDto checkProducts(@RequestBody CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse {
        log.debug("Request for checking cart with id {}", cartDto.getShoppingCartId());
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
