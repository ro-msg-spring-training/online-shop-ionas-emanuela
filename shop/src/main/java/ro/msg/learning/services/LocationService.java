package ro.msg.learning.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.dtos.LocationDTO;
import ro.msg.learning.entities.Location;
import ro.msg.learning.repositories.LocationRepository;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public LocationDTO findById(int id) {

        Location location = locationRepository.findById(id).orElse(null);
        if(null == location) {
            return null;
        }

        return new LocationDTO(location);
    }
}
