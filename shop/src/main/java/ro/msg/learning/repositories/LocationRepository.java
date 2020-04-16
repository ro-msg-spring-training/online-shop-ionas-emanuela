package ro.msg.learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {


}
