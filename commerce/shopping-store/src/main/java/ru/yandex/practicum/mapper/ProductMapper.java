package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.products.ProductDto;

@Component
public class ProductMapper {

    public static ProductDto mapToDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .imageSrc(product.getImage())
                .quantityState(product.getQuantityState())
                .productState(product.getProductState())
                .productCategory(product.getProductCategory())
                .price(product.getPrice())
                .build();
    }

    public static Product mapToEntity(ProductDto productDto) {
        return Product.builder()
                .productName(productDto.getProductName())
                .description(productDto.getDescription())
                .image(productDto.getImageSrc())
                .quantityState(productDto.getQuantityState())
                .productState(productDto.getProductState())
                .productCategory(productDto.getProductCategory())
                .price(productDto.getPrice())
                .build();
    }
}
