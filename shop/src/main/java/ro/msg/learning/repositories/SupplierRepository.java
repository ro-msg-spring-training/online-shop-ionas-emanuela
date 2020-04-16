package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
