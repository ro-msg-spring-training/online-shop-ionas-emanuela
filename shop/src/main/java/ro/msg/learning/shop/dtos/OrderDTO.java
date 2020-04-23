package ro.msg.learning.shop.dtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Order;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class OrderDTO {

    int id;
    Date createdAt;
    Address address;
    CustomerDTO customer;
    LocationDTO location;
    List<ProductDTO> products;

    public OrderDTO(Order order, List<ProductDTO> products) {
        this.id = order.getId();
        this.createdAt = order.getCreatedAt();
        this.address = order.getAddress();
        this.customer = new CustomerDTO(order.getCustomer());
        this.location = new LocationDTO(order.getShippedFrom());
        this.products = products;
    }

}
