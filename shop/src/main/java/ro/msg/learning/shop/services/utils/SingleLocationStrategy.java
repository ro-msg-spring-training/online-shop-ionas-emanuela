package ro.msg.learning.shop.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.dtos.RestockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.repositories.ProductCategoryRepository;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;
import ro.msg.learning.shop.services.LocationService;

import java.util.*;

@RequiredArgsConstructor
@Component
public class SingleLocationStrategy implements IStrategy{

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<RestockDTO> findLocations(OrderInfoDTO order) {

        List<RestockDTO> restockDTOList = new ArrayList<>();
        HashMap<Integer, List<Stock>> possibleLocations = new HashMap<>();
        HashMap<Integer, Integer> locationFrequency = new HashMap<>();

        Iterator it = order.getProducts().entrySet().iterator();
        List<Stock> stockList = new ArrayList<>();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int productId = (int) pair.getKey();
            int quantity = (int) pair.getValue();
            stockList = stockRepository.findAllByProductAndQuantityGreaterThanEqual(Product.builder().id(productId).build(), quantity);

            if(null == stockList || stockList.size() == 0) {
                throw new OrderNotCompletedException("inexistent stock");
            }

            possibleLocations.put(productId, stockList);
        }

        it = possibleLocations.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            stockList = possibleLocations.get(pair.getKey());

            for(Stock stock: stockList) {

                int locationId = stock.getLocation().getId();
                if(locationFrequency.containsKey(locationId)) {
                    locationFrequency.put(locationId, locationFrequency.get(locationId) + 1);
                }
                else {
                    locationFrequency.put(locationId, 1);
                }

            }
        }

        it = locationFrequency.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int locationId = (int) pair.getKey();
            int locationCount = (int) pair.getValue();

            if(locationCount >= order.getProducts().size()) {
                RestockDTO restockDTO;
                Location location = locationRepository.findById(locationId).orElse(null);

                Iterator it2 = order.getProducts().entrySet().iterator();
                while(it2.hasNext()) {

                    Map.Entry pair2 = (Map.Entry)it2.next();

                    Product product = productRepository.findById((Integer) pair2.getKey()).orElse(null);
                    assert product != null;
                    ProductCategory productCategory = productCategoryRepository.findByName(product.getCategory().getName());

                    restockDTO = RestockDTO.builder()
                            .location(new LocationDTO(location))
                            .product(new ProductDTO(product, productCategory))
                            .quantity((Integer) pair2.getValue())
                            .build();

                    restockDTOList.add(restockDTO);
                }

                return restockDTOList;
            }

        }

        throw new OrderNotCompletedException("unexpected error");
    }
}
