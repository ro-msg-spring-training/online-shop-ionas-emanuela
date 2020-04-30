package ro.msg.learning.shop.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.dtos.RestockDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MostAbundantStrategy implements IStrategy{

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Override
    public List<RestockDTO> findLocations(OrderInfoDTO order) {

        List<RestockDTO> restockDTOList = new ArrayList<>();
        Map<Integer, List<Stock>> possibleLocations = new HashMap<Integer, List<Stock>>();

        order.getProducts().forEach((productId, quantity) -> {
            List<Stock> stockList = stockRepository.findAllByProductIdAndQuantityGreaterThanEqual(productId, quantity);

            if(stockList == null || stockList.size() == 0) {
                throw new OrderNotCompletedException("nonexistent stock");
            }

            possibleLocations.put(productId, stockList);
        });

        possibleLocations.forEach((productId, stockList) -> {
            Stock maxStock = Collections.max(stockList, Comparator.comparing(Stock::getQuantity));

            Product product = productRepository.findById(productId).orElse(null);

            RestockDTO restockDTO = RestockDTO.builder()
                    .product(new ProductDTO(product, product.getCategory()))
                    .location(new LocationDTO(maxStock.getLocation()))
                    .quantity(order.getProducts().get(productId))
                    .build();

            restockDTOList.add(restockDTO);
        });

        return restockDTOList;
    }
}
