package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    void deleteById(Integer id);

}
