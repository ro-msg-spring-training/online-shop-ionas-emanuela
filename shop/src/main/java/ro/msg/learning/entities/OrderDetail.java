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
    @MapsId("id")
    @JoinColumn(name = "order")
    Order order;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "product")
    Product product;

    @Column(name = "quantity")
    int quantity;

}
