package ro.msg.learning.shop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "stock", schema = "shop_schema")
@Entity
public class Stock {

    @EmbeddedId
    StockKey id;

    @ManyToOne
    //@MapsId("id")
    @JoinColumn(name = "productid", referencedColumnName = "id", insertable = false, updatable = false)
    Product product;

    @ManyToOne
    //@MapsId("id")
    @JoinColumn(name = "locationid", referencedColumnName = "id", insertable = false, updatable = false)
    Location location;

    @Column(name = "quantity")
    Integer quantity;

}
