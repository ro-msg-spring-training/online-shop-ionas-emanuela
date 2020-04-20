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
@Table(name = "supplier", schema = "shop_schema")
@Entity
public class Supplier implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "name", length = 100)
    String name;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    List<Product> productList = new ArrayList<>();
}
