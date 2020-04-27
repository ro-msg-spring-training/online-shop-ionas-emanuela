package ro.msg.learning.shop.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;

import java.util.Date;
import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class OrderInfoDTO {

    Date timestamp;
    Address deliveryAddress;
    LocationDTO shopAddress;
    HashMap<Integer, Integer> products;

}
