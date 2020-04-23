package ro.msg.learning.shop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "product", schema = "shop_schema")
@Entity
public class Product {

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
    @JoinColumn(name = "categoryid")
    ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "supplierid")
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
