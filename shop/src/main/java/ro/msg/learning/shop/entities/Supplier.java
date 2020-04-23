package ro.msg.learning.shop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "supplier", schema = "shop_schema")
@Entity
public class Supplier {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "name", length = 100)
    String name;

    //@OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    //List<Product> productList = new ArrayList<>();
}
