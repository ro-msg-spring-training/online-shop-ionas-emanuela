package ro.msg.learning.shop.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.services.OrderService;
import ro.msg.learning.shop.services.ProductService;
import ro.msg.learning.shop.services.utils.OrderNotCompletedException;

import java.util.Date;
import java.util.HashMap;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class OrderControllerTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Test
    void orderControllerTestCreateSuccessful() {

        Address address = Address.builder().city("Targu Lapus").country("Romania").county("Maramures").streetAddress("L. Rebreanu, 3").build();
        address.setStreetAddress("L. Rebreanu, 60");
        LocationDTO locationDTO = LocationDTO.builder().id(1).address(address).name("warehouse #3").build();

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1, 10);
        products.put(2, 15);
        products.put(3,20);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().timestamp(new Date()).deliveryAddress(address).shopAddress(locationDTO).products(products).build();

        OrderController orderController = new OrderController(orderService, productService);

        Assertions.assertNotNull(orderController.createOrder(orderInfoDTO));
    }

    @Test
    void orderControllerTestCreateStockError() {

        Address address = Address.builder().city("Targu Lapus").country("Romania").county("Maramures").streetAddress("L. Rebreanu, 3").build();
        address.setStreetAddress("L. Rebreanu, 60");
        LocationDTO locationDTO = LocationDTO.builder().id(1).address(address).name("warehouse #3").build();

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1, 10);
        products.put(2, 400);
        products.put(3,20);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().timestamp(new Date()).deliveryAddress(address).shopAddress(locationDTO).products(products).build();

        OrderController orderController = new OrderController(orderService, productService);

        Assertions.assertThrows(OrderNotCompletedException.class, () -> {
           orderController.createOrder(orderInfoDTO);
        });

    }

}