package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dtos.CustomerDTO;
import ro.msg.learning.shop.dtos.LocationDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.entities.Order;

import java.util.List;

public interface IOrderService {

    Order createOrder(OrderInfoDTO toSave);
    List<Order> findAllOrders();
    Order findOrderById(int id);

}
