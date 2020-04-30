package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockKey;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, StockKey> {

    void deleteByProductId(Integer id);
    List<Stock> findAllByProductAndQuantityGreaterThanEqual(Product product, Integer quantity);
    List<Stock> findAllByProductIdAndQuantityGreaterThanEqual(Integer id, Integer quantity);
    Stock findByProductAndLocation(Product product, Location location);
    List<Stock> findByProductId(Integer id);
    void deleteAll();

}
