package ro.msg.learning.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.services.utils.EntityNotFoundException;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location findLocationById(int id) {

        return locationRepository.findById(id).orElse(null);

    }
}
