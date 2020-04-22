package ro.msg.learning.services;

import ro.msg.learning.dtos.LocationDTO;

public interface ILocationService {

    LocationDTO findById(int id);

}
