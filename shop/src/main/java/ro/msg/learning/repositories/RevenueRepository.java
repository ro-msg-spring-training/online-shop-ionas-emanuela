package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Revenue;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
}
