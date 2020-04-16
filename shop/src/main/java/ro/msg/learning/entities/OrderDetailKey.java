package ro.msg.learning.entities;

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
public class OrderDetailKey implements Serializable {

    @Column(name = "order")
    int orderId;

    @Column(name = "product")
    int productId;

}
