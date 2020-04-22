package ro.msg.learning.entities;


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
public class OrderDetail implements IColumn{

    @EmbeddedId
    OrderDetailKey id;

    @ManyToOne
    //@MapsId("id")
    //@JoinColumn(name = "order")
    @JoinColumn(name = "orderid", referencedColumnName = "id", insertable = false, updatable = false)
    Order order;

    @ManyToOne
    //@MapsId("id")
    //@JoinColumn(name = "product")
    @JoinColumn(name = "productid", referencedColumnName = "id", insertable = false, updatable = false)
    Product product;

    @Column(name = "quantity")
    int quantity;

}
