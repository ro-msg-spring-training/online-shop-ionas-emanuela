package ro.msg.learning.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.EAN;
import ro.msg.learning.entities.Address;

import java.util.Date;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class OrderDTO {

    Date timestamp;
    Address deliveryAddress;
    HashMap products = new HashMap<Integer, Integer>();

}
