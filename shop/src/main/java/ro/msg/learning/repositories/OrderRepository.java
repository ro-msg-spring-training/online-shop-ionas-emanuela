package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
