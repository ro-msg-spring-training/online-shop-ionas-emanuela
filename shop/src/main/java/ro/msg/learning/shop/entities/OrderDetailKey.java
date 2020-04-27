package ro.msg.learning.shop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Embeddable
public class OrderDetailKey implements Serializable {

    @Column(name = "orderid")
    Integer orderId;

    @Column(name = "productid")
    Integer productId;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof OrderDetailKey)) return false;
        OrderDetailKey orderDetailKey = (OrderDetailKey) o;
        return Objects.equals(getOrderId(), orderDetailKey.getOrderId()) &&
                Objects.equals(getProductId(), orderDetailKey.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getOrderId());
    }

}
