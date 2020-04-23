package ro.msg.learning.shop.services.utils;

import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.RestockDTO;

import java.util.List;


public interface IStrategy {

    List<RestockDTO> findLocations(OrderInfoDTO order);

}
