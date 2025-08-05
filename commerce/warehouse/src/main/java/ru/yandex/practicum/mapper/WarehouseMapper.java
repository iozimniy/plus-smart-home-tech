package ru.yandex.practicum.mapper;

import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.warehouse.NewProductInWarehouseRequest;

public class WarehouseMapper {

    public static Product mapToProductFromNewProduct(NewProductInWarehouseRequest request) {
        return Product.builder()
                .id(request.getProductId())
                .fragile(request.getFragile())
                .weight(request.getWeight())
                .height(request.getDimension().getHeight())
                .depth(request.getDimension().getDepth())
                .width(request.getDimension().getWidth())
                .quantity(0)
                .build();
    }
}
