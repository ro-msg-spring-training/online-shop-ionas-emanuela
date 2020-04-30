package ro.msg.learning.shop.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.dtos.RestockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.*;

@RequiredArgsConstructor
@Component
public class SingleLocationStrategy implements IStrategy{

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<RestockDTO> findLocations(OrderInfoDTO order) {

        List<RestockDTO> restockDTOList = new ArrayList<>();
        HashMap<Integer, List<Stock>> possibleLocations = new HashMap<>();
        HashMap<Integer, Integer> locationFrequency = new HashMap<>();

        order.getProducts().forEach((productId, quantity) -> {
            List<Stock> stockList = stockRepository.findAllByProductIdAndQuantityGreaterThanEqual(productId, quantity);

            if(null == stockList || stockList.size() == 0) {
                throw new OrderNotCompletedException("nonexistent stock");
            }

            possibleLocations.put(productId, stockList);
        });

        possibleLocations.forEach((productId, stockList) -> {
            stockList.forEach(stock -> {
                int locationId = stock.getLocation().getId();
                if(locationFrequency.containsKey(locationId)){
                    locationFrequency.put(locationId, locationFrequency.get(locationId) + 1);
                }
                else {
                    locationFrequency.put(locationId, 1);
                }
            });
        });

        locationFrequency.forEach((locationId, locationCount) -> {
            if(locationCount >= order.getProducts().size()) {
                Location location = locationRepository.findById(locationId).orElse(null);

                order.getProducts().forEach((productId, quantity) -> {
                    Product product = productRepository.findById(productId).orElse(null);
                    RestockDTO restockDTO = RestockDTO.builder()
                            .location(new LocationDTO(location))
                            .product(new ProductDTO(product, product.getCategory()))
                            .quantity(quantity)
                            .build();

                    restockDTOList.add(restockDTO);
                });

            }
        });

        return restockDTOList;

    }
}
