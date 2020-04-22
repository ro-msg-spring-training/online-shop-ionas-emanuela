package ro.msg.learning.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Location;
import ro.msg.learning.entities.Product;
import ro.msg.learning.entities.Stock;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

   // List<Location> findAllByStockListIsContaining(Stock stock);
}
