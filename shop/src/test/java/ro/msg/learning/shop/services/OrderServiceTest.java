package ro.msg.learning.shop.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.utils.MostAbundantStrategy;
import ro.msg.learning.shop.services.utils.OrderNotCompletedException;

import java.util.Date;
import java.util.HashMap;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class OrderServiceTest {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MostAbundantStrategy mostAbundantStrategy;

    @Test
    void orderServiceTestCreateSuccessful() {

        Address address = Address.builder().city("Targu Lapus").country("Romania").county("Maramures").streetAddress("L. Rebreanu, 3").build();
        address.setStreetAddress("L. Rebreanu, 60");
        LocationDTO locationDTO = LocationDTO.builder().id(1).address(address).name("warehouse #3").build();

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1, 10);
        products.put(2, 15);
        products.put(3, 20);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().timestamp(new Date()).deliveryAddress(address).shopAddress(locationDTO).products(products).build();

        OrderService orderService = new OrderService(stockRepository, orderRepository, customerRepository,
                locationRepository, orderDetailRepository, productRepository, mostAbundantStrategy);

        int stock1Prev = stockRepository.findByProductId(1).get(0).getQuantity();
        int stock2Prev = stockRepository.findByProductId(2).get(0).getQuantity();
        int stock3Prev = stockRepository.findByProductId(3).get(0).getQuantity();

        int orderPrev = orderRepository.findAll().size();

        Order order = orderService.createOrder(orderInfoDTO);

        Assertions.assertNotNull(order);

        int stock1After = stockRepository.findByProductId(1).get(0).getQuantity();
        int stock2After = stockRepository.findByProductId(2).get(0).getQuantity();
        int stock3After = stockRepository.findByProductId(3).get(0).getQuantity();

        Assertions.assertEquals(stock1After, stock1Prev - 10);
        Assertions.assertEquals(stock2After, stock2Prev - 15);
        Assertions.assertEquals(stock3After, stock3Prev - 20);

        Assertions.assertEquals(orderPrev + 1, orderRepository.findAll().size());
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

        OrderService orderService = new OrderService(stockRepository, orderRepository, customerRepository,
                locationRepository, orderDetailRepository, productRepository, mostAbundantStrategy);

        Assertions.assertThrows(OrderNotCompletedException.class, () -> orderService.createOrder(orderInfoDTO));

    }

}