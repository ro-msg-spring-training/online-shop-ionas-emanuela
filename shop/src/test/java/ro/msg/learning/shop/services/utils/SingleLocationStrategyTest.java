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
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.repositories.ProductCategoryRepository;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class SingleLocationStrategyTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private SingleLocationStrategy singleLocationStrategy;

    @Test
    void testSingleLocationStrategySuccessful() {

        ProductCategory productCategory = ProductCategory.builder().id(1).name("").description("").build();
        Product product1 = Product.builder().id(1).supplier(new Supplier()).category(productCategory).build();
        Product product2 = Product.builder().id(2).supplier(new Supplier()).category(productCategory).build();

        Mockito.when(productRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(product1));
        Mockito.when(productRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(product2));

        Location location1 = Location.builder().id(1).build();
        Location location2 = Location.builder().id(2).build();

        Stock stock1 = Stock.builder().location(location1).build();
        Stock stock2 = Stock.builder().location(location2).build();

        List<Stock> stockList1 = new ArrayList<>();
        stockList1.add(stock1);
        stockList1.add(stock2);

        List<Stock> stockList2 = new ArrayList<>();
        stockList2.add(stock1);

        Mockito.when(stockRepository.findAllByProductAndQuantityGreaterThanEqual(product1, 10)).thenReturn(stockList1);
        Mockito.when(stockRepository.findAllByProductAndQuantityGreaterThanEqual(product2, 20)).thenReturn(stockList2);

        Mockito.when(locationRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(location1));

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1,10);
        products.put(2,20);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().deliveryAddress(new Address())
                .shopAddress(new LocationDTO()).products(products).build();

        List<RestockDTO> restockDTOS = singleLocationStrategy.findLocations(orderInfoDTO);

        Assertions.assertEquals(2, restockDTOS.size());
        Assertions.assertEquals(1, restockDTOS.get(0).getLocation().getId());
    }

    @Test
    void testSingleLocationStrategyProductError() {

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1,10);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().deliveryAddress(new Address())
                .shopAddress(new LocationDTO()).products(products).build();

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            singleLocationStrategy.findLocations(orderInfoDTO);
        });

        Mockito.verify(productRepository, Mockito.times(1)).findById(1);

        Mockito.verifyNoMoreInteractions(productRepository);
        Mockito.verifyNoMoreInteractions(stockRepository);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void testSingleLocationStrategyStockError() {

        Product product1 = Product.builder().id(1).supplier(new Supplier()).category(new ProductCategory()).build();
        Mockito.when(productRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(product1));
        Mockito.when(stockRepository.findAllByProductAndQuantityGreaterThanEqual(product1, 10)).thenReturn(null);

        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(1,10);

        OrderInfoDTO orderInfoDTO = OrderInfoDTO.builder().deliveryAddress(new Address())
                .shopAddress(new LocationDTO()).products(products).build();

        Assertions.assertThrows(OrderNotCompletedException.class, () -> {
            singleLocationStrategy.findLocations(orderInfoDTO);
        });

        Mockito.verify(productRepository, Mockito.times(1)).findById(1);
        Mockito.verify(stockRepository, Mockito.times(1)).findAllByProductAndQuantityGreaterThanEqual(product1, 10);

        Mockito.verifyNoMoreInteractions(productRepository);
        Mockito.verifyNoMoreInteractions(stockRepository);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

}































































































