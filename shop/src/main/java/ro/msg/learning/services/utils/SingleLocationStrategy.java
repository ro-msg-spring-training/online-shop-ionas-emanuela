package ro.msg.learning.services.utils;

import org.springframework.beans.factory.annotation.Autowired;
import ro.msg.learning.dtos.LocationDTO;
import ro.msg.learning.dtos.OrderDTO;
import ro.msg.learning.dtos.ProductDTO;
import ro.msg.learning.dtos.RestockDTO;
import ro.msg.learning.entities.Product;
import ro.msg.learning.entities.ProductCategory;
import ro.msg.learning.entities.Stock;
import ro.msg.learning.repositories.ProductCategoryRepository;
import ro.msg.learning.repositories.ProductRepository;
import ro.msg.learning.repositories.StockRepository;
import ro.msg.learning.services.LocationService;

import java.util.*;

public class SingleLocationStrategy implements IStrategy{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private LocationService locationService;

    @Override
    public List<RestockDTO> findLocations(OrderDTO order) {

        List<RestockDTO> restockDTOList = new ArrayList<>();
        HashMap<Integer, List<Stock>> possibleLocations = new HashMap<>();
        HashMap<Integer, Integer> locationFrequency = new HashMap<>();

        Iterator it = order.getProducts().entrySet().iterator();
        List<Stock> stockList;

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
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
            Map.Entry pair = (Map.Entry)it.next();
            stockList = (List<Stock>) pair.getValue();

            for(Stock stock: stockList) {
                int locationId = stock.getLocation().getId();
                if(locationFrequency.containsKey(locationId)) {
                    locationFrequency.put(locationId, locationFrequency.get(locationId) + 1);
                }
                else {
                    locationFrequency.put(locationId, 0);
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
                LocationDTO locationDTO = locationService.findById(locationId);

                Iterator it2 = order.getProducts().entrySet().iterator();
                while(it2.hasNext()) {

                    Map.Entry pair2 = (Map.Entry)it2.next();

                    Product product = productRepository.findById((Integer) pair2.getKey()).orElse(null);
                    assert product != null;
                    ProductCategory productCategory = productCategoryRepository.findByName(product.getCategory().getName());

                    restockDTO = RestockDTO.builder()
                            .location(locationDTO)
                            .product(new ProductDTO(product, productCategory))
                            .quantity((Integer) pair2.getValue())
                            .build();

                    restockDTOList.add(restockDTO);
                }

                return restockDTOList;
            }

        }

        throw new OrderNotCompletedException();
    }
}
