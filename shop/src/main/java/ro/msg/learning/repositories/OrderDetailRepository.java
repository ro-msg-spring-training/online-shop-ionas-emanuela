package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Order;
import ro.msg.learning.entities.OrderDetail;
import ro.msg.learning.entities.OrderDetailKey;
import ro.msg.learning.entities.Product;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailKey> {

    void deleteByProductId(Integer id);
    List<OrderDetail> findAllByOrder(Order order);
}
