package ro.msg.learning.services.utils;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.dtos.LocationDTO;
import ro.msg.learning.dtos.OrderDTO;
import ro.msg.learning.dtos.ProductDTO;
import ro.msg.learning.dtos.RestockDTO;
import ro.msg.learning.entities.Location;
import ro.msg.learning.entities.Product;
import ro.msg.learning.entities.ProductCategory;
import ro.msg.learning.entities.Stock;
import ro.msg.learning.repositories.LocationRepository;
import ro.msg.learning.repositories.ProductCategoryRepository;
import ro.msg.learning.repositories.ProductRepository;
import ro.msg.learning.repositories.StockRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class MostAbundantStrategy implements IStrategy{

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<RestockDTO> findLocations(OrderDTO order) {

        List<RestockDTO> restockDTOList = new ArrayList<>();
        HashMap<Integer, List<Stock>> possibleLocations = new HashMap<>();

        Iterator it = order.getProducts().entrySet().iterator();
        List<Stock> stockList;

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int productId = (int) pair.getKey();
            int quantity = (int) pair.getValue();

            stockList = stockRepository.findAllByProductAndQuantityGreaterThanEqual(Product.builder().id(productId).build(), quantity);

            if(null == stockList || stockList.size() == 0) {
                throw new OrderNotCompletedException();
            }

            possibleLocations.put(productId, stockList);

        }

        it = possibleLocations.entrySet().iterator();

        while(it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            int productId = (int) pair.getKey();
            stockList = (List<Stock>) pair.getKey();

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
                    .quantity((Integer) order.getProducts().get(productId))
                    .build();

            restockDTOList.add(restockDTO);

        }


        return restockDTOList;
    }
}
