package ro.msg.learning.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "product", schema = "shop_schema")
@Entity
public class Product implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", length = 100)
    String name;

    @Column(name = "description", length = 300)
    String description;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "weight")
    Double weight;

    @ManyToOne
    @JoinColumn(name = "category")
    ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "supplier")
    Supplier supplier;

    @Column(name = "image_url", length = 300)
    String imageUrl;

    //@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    //List<Stock> stockList = new ArrayList<>();

    @Override
    public String toString() {
        return "name: " + name + " description: " + description +
                " price: " + price + " weight: " + weight;
    }

}
