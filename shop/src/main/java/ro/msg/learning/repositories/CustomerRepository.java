package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    
}
