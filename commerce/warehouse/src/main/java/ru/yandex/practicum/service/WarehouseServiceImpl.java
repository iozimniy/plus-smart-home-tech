package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.cart.CartProductDto;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.*;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.mapper.WarehouseMapper.mapToProductFromNewProduct;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository repository;

    private static final String[] ADDRESSES =
            new String[] {"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    @Transactional
    @Override
    public void putProduct(NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException {
        log.info("Request for add new product {}", request);
        log.debug("Request for add new product {} with id {}", request, request.getProductId());
        if (repository.existsById(request.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException("Tовар уже зарегистрирован на складе");
        }

        repository.save(mapToProductFromNewProduct(request));
    }

    @Override
    public BookedProductsDto checkProducts(CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse {
        log.info("Request for check products {}", cartDto);
        List<UUID> listId = cartDto.getProducts().keySet().stream().toList();
//        Map<UUID, Product> products = repository.findAllById(listId)
//                .stream().collect(Collectors.toMap(
//                        s -> s.getId(),
//                        s -> s
//                ));
        
        List<Product> products = repository.findAllById(listId);

        Double deliveryWeight = 0.0;
        Double deliveryVolume = 0.0;
        Boolean fragile = false;

        for (Product product : products) {
            if (product.getQuantity() < cartDto.getProducts().get(product.getId())) {
                throw new ProductInShoppingCartLowQuantityInWarehouse("Товар c id" + product.getId() +
                        " не присутствует в требуемом количестве");
            }

            Double volume = product.getWidth() * product.getHeight() * product.getDepth();
            deliveryVolume = deliveryVolume + volume;
            deliveryWeight = deliveryWeight + product.getWeight();

            if (!fragile && product.getFragile()) {
                fragile = true;
            }
        }

        //тут идёт итерация по продуктам из CartDto, а у нас там теперь map, а не list
        //нужно по чему-то итерарироваться, мб, по листу
//        for (CartProductDto product : cartDto.getProducts()) {
//            Product thisProduct = products.get(product.getId());
//
//            if (products.get(product.getId()).getQuantity() < product.getQuantity()) {
//                throw new ProductInShoppingCartLowQuantityInWarehouse("Товар c id" + product.getId() +
//                        " не присутствует в требуемом количестве");
//            }
//
//            Double volume = thisProduct.getWidth() * thisProduct.getHeight() * thisProduct.getDepth();
//            deliveryVolume = deliveryVolume + volume;
//            deliveryWeight = deliveryWeight + thisProduct.getWeight();
//
//            if (!fragile && thisProduct.getFragile()) {
//                fragile = true;
//            }
//        }

        return BookedProductsDto.builder()
                .deliveryVolume(deliveryVolume)
                .deliveryWeight(deliveryWeight)
                .fragile(fragile)
                .build();
    }

    @Override
    @Transactional
    public void addQuantity(AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException {
        log.info("Request for adding quantity {} of product {}", request.getQuantity(), request.getProductId());

        if (!repository.existsById(request.getProductId())) {
            throw new NoSpecifiedProductInWarehouseException("Нет информации о товаре на складе");
        }

        Product product = repository.findById(request.getProductId()).get();
        var quantity = product.getQuantity() + request.getQuantity();
        product.setQuantity(quantity);

        repository.save(product);
    }

    @Override
    public AddressDto getAddress() {
        return AddressDto.builder()
                .country(CURRENT_ADDRESS)
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }
}
