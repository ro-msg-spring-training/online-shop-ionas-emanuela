package ro.msg.learning.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "product_category", schema = "shop_schema")
@Entity
public class ProductCategory implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", length = 100)
    String name;

    @Column(name = "description", length = 300)
    String description;

    @Override
    public String toString() {
        return name + ": " + description;
    }

}
