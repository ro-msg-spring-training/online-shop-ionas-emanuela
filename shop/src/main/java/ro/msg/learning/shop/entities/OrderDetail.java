package ro.msg.learning.shop.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "order_detail", schema = "shop_schema")
@Entity
public class OrderDetail {

    @EmbeddedId
    OrderDetailKey id;

    @ManyToOne
    @JoinColumn(name = "productid", referencedColumnName = "id", insertable = false, updatable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "orderid", referencedColumnName = "id", insertable = false, updatable = false)
    Order order;

    @Column(name = "quantity")
    int quantity;

}
