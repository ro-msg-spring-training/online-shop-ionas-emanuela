package ro.msg.learning.shop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Embeddable
public class StockKey implements Serializable {

    @Column(name = "productid")
    Integer productId;

    @Column(name = "locationid")
    Integer locationId;

}
