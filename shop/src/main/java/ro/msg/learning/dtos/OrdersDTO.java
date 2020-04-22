package ro.msg.learning.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.msg.learning.entities.Address;
import ro.msg.learning.entities.Order;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class OrdersDTO {

    int id;
    Date createdAt;
    Address address;
    CustomerDTO customer;
    LocationDTO location;
    List<ProductDTO> products;

    public OrdersDTO(Order order, List<ProductDTO> productDTOS) {
        this.id = order.getId();
        this.createdAt = order.getCreatedAt();
        this.address = order.getAddress();
        this.customer = new CustomerDTO(order.getCustomer());
        this.location = new LocationDTO(order.getShippedFrom());
        this.products = productDTOS;
    }

}
