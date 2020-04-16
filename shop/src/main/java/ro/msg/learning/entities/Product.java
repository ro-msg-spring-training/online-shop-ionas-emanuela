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
@Table(name = "product")
@Entity
public class Product implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "name", length = 100)
    String name;

    @Column(name = "description", length = 300)
    String description;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "weight")
    Double weight;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "category")
    ProductCategory productCategory;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "supplier")
    Supplier supplier;

    @Column(name = "image_url", length = 300)
    String imageUrl;

    @OneToMany(mappedBy = "product")
    List<Stock> stockList = new ArrayList<>();

}
