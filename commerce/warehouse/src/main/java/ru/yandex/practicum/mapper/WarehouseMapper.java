package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.warehouse.NewProductInWarehouseRequest;

@Component
public class WarehouseMapper {

    public static Product mapToProductFromNewProduct(NewProductInWarehouseRequest request) {
        return Product.builder()
                .id(request.getProductId())
                .fragile(request.getFragile())
                .weight(request.getWeight())
                .height(request.getDimension().getHeight())
                .depth(request.getDimension().getDepth())
                .width(request.getDimension().getWidth())
                .quantity(0L)
                .build();
    }
}
