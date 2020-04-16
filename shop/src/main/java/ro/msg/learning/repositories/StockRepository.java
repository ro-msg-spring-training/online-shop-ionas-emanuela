package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Location;
import ro.msg.learning.entities.Product;
import ro.msg.learning.entities.Stock;
import ro.msg.learning.entities.StockKey;

@Repository
public interface StockRepository extends JpaRepository<Stock, StockKey> {

    Stock findByLocation(Location location);

    Stock findByProduct(Product product);

    Stock findByProductAndLocation(Product product, Location location);

}
