package ro.msg.learning.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "stock")
@Entity
public class Stock implements IColumn{

    @EmbeddedId
    StockKey id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "product")
    Product product;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "location")
    Location location;

    @Column(name = "quantity")
    int quantity;

}
