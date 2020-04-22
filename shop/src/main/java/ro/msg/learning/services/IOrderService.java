package ro.msg.learning.services;

import ro.msg.learning.dtos.CustomerDTO;
import ro.msg.learning.dtos.LocationDTO;
import ro.msg.learning.dtos.OrderDTO;
import ro.msg.learning.entities.Order;

public interface IOrderService {

    Order createOrder(OrderDTO orderDTO, CustomerDTO customerDTO, LocationDTO locationDTO);

}
