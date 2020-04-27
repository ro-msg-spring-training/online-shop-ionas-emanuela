package ro.msg.learning.shop.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Order;

import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class OrderDTO {

    int id;
    Date createdAt;
    Address address;
    CustomerDTO customer;
    LocationDTO location;
    Map<ProductDTO,Integer> products;

    public OrderDTO(Order order, Map<ProductDTO, Integer> products) {
        this.id = order.getId();
        this.createdAt = order.getCreatedAt();
        this.address = order.getAddress();
        this.customer = new CustomerDTO(order.getCustomer());
        this.location = new LocationDTO(order.getShippedFrom());
        this.products = products;
    }

}
