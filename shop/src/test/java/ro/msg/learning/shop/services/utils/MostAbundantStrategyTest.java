package ro.msg.learning.shop.services.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.RestockDTO;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class MostAbundantStrategyTest {

    @Mock
    private StockRepository stockRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private MostAbundantStrategy mostAbundantStrategy;

    @Test
    void testMostAbundantStrategySuccessful() {

        ProductCategory productCategory = ProductCategory.builder().id(1).name("").description("").build();
        Product product1 = Product.builder().id(1).supplier(new Supplier()).category(productCategory).build();
        Product product2 = Product.builder().id(2).supplier(new Supplier()).category(productCategory).build();

        Mockito.when(productRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(product1));
        Mockito.when(productRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(product2));

        Location location1 = Location.builder().id(1).build();
        Location location2 = Location.builder().id(2).build();

        Stock stock1 = Stock.builder().location(location1).product(product1).quantity(100).build();
        Stock stock2 = Stock.builder().location(location2).product(product1).quantity(50).build();
        Stock stock3 = Stock.builder().location(location1).product(product2).quantity(150).build();
        Stock stock4 = Stock.builder().location(location2).product(product2).quantity(200).build();

        List<Stock> stockList1 = new ArrayList<>();
        stockList1.add(stock1);
        stockList1.add(stock2);

        List<Stock> stockList2 = new ArrayList<>();
        stockList2.add(stock3);
        stockList2.add(stock4);

        Mockito.when(stockRepository.findAllByProductIdAndQuantityGreaterThanEqual(1, 10)).thenReturn(stockList1);
        Mockito.when(stockRepository.findAllByProductIdAndQuantityGreaterThanEqual(2, 20)).thenReturn(stockList2);

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1,10);
        products.put(2,20);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().deliveryAddress(new Address())
                .shopAddress(new LocationDTO()).products(products).build();

        List<RestockDTO> restockDTOS = mostAbundantStrategy.findLocations(orderInfoDTO);

        Assertions.assertEquals(2, restockDTOS.size());

    }

    @Test
    void testMostAbundantStrategyProductError() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1,10);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().deliveryAddress(new Address())
                .shopAddress(new LocationDTO()).products(products).build();

        Mockito.when(stockRepository.findAllByProductIdAndQuantityGreaterThanEqual(1, 10)).thenReturn(null);

        Assertions.assertThrows(OrderNotCompletedException.class, () ->{
            mostAbundantStrategy.findLocations(orderInfoDTO);
        });

        Mockito.verify(stockRepository, Mockito.times(1)).findAllByProductIdAndQuantityGreaterThanEqual(1, 10);

    }

    @Test
    void testMostAbundantStrategyStockError() {

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1,10);

        Mockito.when(stockRepository.findAllByProductIdAndQuantityGreaterThanEqual(1, 10)).thenReturn(null);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().deliveryAddress(new Address())
                .shopAddress(new LocationDTO()).products(products).build();

        Assertions.assertThrows(OrderNotCompletedException.class, () -> {
            mostAbundantStrategy.findLocations(orderInfoDTO);
        });

        Mockito.verify(stockRepository, Mockito.times(1)).findAllByProductIdAndQuantityGreaterThanEqual(1, 10);
    }

}