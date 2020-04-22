package ro.msg.learning.services.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.msg.learning.dtos.OrderDTO;
import ro.msg.learning.dtos.RestockDTO;

import java.util.List;


public interface IStrategy {

    List<RestockDTO> findLocations(OrderDTO order);

}
