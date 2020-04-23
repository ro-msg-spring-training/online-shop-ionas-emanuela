package ro.msg.learning.shop.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.dtos.RestockDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.repositories.ProductCategoryRepository;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MostAbundantStrategy implements IStrategy{

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<RestockDTO> findLocations(OrderInfoDTO order) {

        List<RestockDTO> restockDTOList = new ArrayList<>();
        Map<Integer, List<Stock>> possibleLocations = new HashMap<Integer, List<Stock>>();

        Iterator it = order.getProducts().entrySet().iterator();
        List<Stock> stockList;

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int productId = (int) pair.getKey();
            int quantity = (int) pair.getValue();

            stockList = stockRepository.findAllByProductAndQuantityGreaterThanEqual(Product.builder().id(productId).build(), quantity);

            if(null == stockList || stockList.size() == 0) {
                throw new OrderNotCompletedException("not enough products in stock");
            }

            possibleLocations.put(productId, stockList);

        }

        it = possibleLocations.entrySet().iterator();

        while(it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            int productId = (int) pair.getKey();
            stockList = possibleLocations.get(productId);

            int max = 0;
            Stock maxStock = new Stock();

            for(Stock stock: stockList) {
                if(stock.getQuantity() > max) {
                    max = stock.getQuantity();
                    maxStock = stock;
                }
            }

            Product product = productRepository.findById(productId).orElse(null);
            ProductCategory productCategory = productCategoryRepository.findByName(product.getCategory().getName());

            RestockDTO restockDTO = RestockDTO.builder()
                    .product(new ProductDTO(product, productCategory))
                    .location(new LocationDTO(maxStock.getLocation()))
                    .quantity(order.getProducts().get(productId))
                    .build();

            restockDTOList.add(restockDTO);

        }


        return restockDTOList;
    }
}
