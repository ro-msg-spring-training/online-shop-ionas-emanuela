package ro.msg.learning.shop.dtos;


import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class RestockDTO {

    LocationDTO location;
    ProductDTO product;
    int quantity;
}
