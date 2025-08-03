package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.cart.CartDto;
import ru.yandex.practicum.model.OrderBooking;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.OrderBookingRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.warehouse.*;

import java.security.SecureRandom;
import java.util.*;

import static ru.yandex.practicum.mapper.WarehouseMapper.mapToProductFromNewProduct;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];
    private final WarehouseRepository warehouseRepository;
    private final OrderBookingRepository orderBookingRepository;


    @Transactional
    @Override
    public void putProduct(NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException {
        log.info("Request for add new product {}", request);
        log.debug("Request for add new product {} with id {}", request, request.getProductId());
        if (warehouseRepository.existsById(request.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException("Tовар уже зарегистрирован на складе");
        }

        warehouseRepository.save(mapToProductFromNewProduct(request));
    }

    @Override
    public BookedProductsDto checkProducts(CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse {
        log.info("Request for check products {}", cartDto);
        List<UUID> listId = cartDto.getProducts().keySet().stream().toList();
        log.debug("Список UUID: {}", listId);

        List<Product> products = warehouseRepository.findByIdIn(listId);
        log.debug("Список продуктов: {}", products.stream().map(Product::getId).toList());

        return assemblyInfo(cartDto.getProducts(), products);
    }

    @Override
    @Transactional
    public void addQuantity(AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException {
        log.info("Request for adding quantity {} of product {}", request.getQuantity(), request.getProductId());

        if (!warehouseRepository.existsById(request.getProductId())) {
            throw new NoSpecifiedProductInWarehouseException("Нет информации о товаре на складе");
        }

        Product product = warehouseRepository.findById(request.getProductId()).get();
        var quantity = product.getQuantity() + request.getQuantity();
        product.setQuantity(quantity);

        warehouseRepository.save(product);
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

    @Override
    public void sentProducts(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        OrderBooking orderBooking = orderBookingRepository.findByOrderId(shippedToDeliveryRequest.getOrderId()).get();
        orderBooking.setDeliveryId(shippedToDeliveryRequest.getDeliveryId());

        orderBookingRepository.save(orderBooking);

    }

    @Override
    public void returnProducts(Map<UUID, Integer> returnProducts) {
        log.info("Request for return products {}", returnProducts);
        List<Product> products = warehouseRepository.findByIdIn(returnProducts.keySet().stream().toList());
        ArrayList<Product> updateProducts = new ArrayList<>();

        for (Product product : products) {
            product.setQuantity(product.getQuantity() + returnProducts.get(product.getId()));
            updateProducts.add(product);
        }

        warehouseRepository.saveAll(products);
    }

    @Override
    public BookedProductsDto assembly(AssemblyProductsForOrderRequest assemblyProductsForOrderRequest)
            throws ProductInShoppingCartLowQuantityInWarehouse {
        List<UUID> listId = assemblyProductsForOrderRequest.getProducts().keySet().stream().toList();
        List<Product> products = warehouseRepository.findByIdIn(listId);

        BookedProductsDto bookedProductsDto = assemblyInfo(assemblyProductsForOrderRequest.getProducts(), products);

        ArrayList<Product> updateProducts = new ArrayList<>();

        for (Product product : products) {
            product.setQuantity(product.getQuantity() - assemblyProductsForOrderRequest.getProducts()
                    .get(product.getId()));
            updateProducts.add(product);
        }

        warehouseRepository.saveAll(updateProducts);

        OrderBooking orderBooking = OrderBooking.builder()
                .orderId(assemblyProductsForOrderRequest.getOrderId())
                .build();

        orderBookingRepository.save(orderBooking);


        return bookedProductsDto;
    }

    private BookedProductsDto assemblyInfo(Map<UUID, Integer> productsForAssembly, List<Product> products) throws ProductInShoppingCartLowQuantityInWarehouse {

        double deliveryWeight = 0.0;
        double deliveryVolume = 0.0;
        boolean fragile = false;

        for (Product product : products) {
            if (product.getQuantity() < productsForAssembly.get(product.getId())) {
                throw new ProductInShoppingCartLowQuantityInWarehouse("Товар c id" + product.getId() +
                        " не присутствует в требуемом количестве");
            }

            double volume = product.getWidth() * product.getHeight() * product.getDepth();
            deliveryVolume = deliveryVolume + volume;
            deliveryWeight = deliveryWeight + product.getWeight();

            if (!fragile && product.getFragile()) {
                fragile = true;
            }
        }

        return BookedProductsDto.builder()
                .deliveryVolume(deliveryVolume)
                .deliveryWeight(deliveryWeight)
                .fragile(fragile)
                .build();
    }
}
