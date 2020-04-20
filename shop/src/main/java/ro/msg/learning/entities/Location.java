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
@Table(name = "location", schema = "shop_schema")
@Entity
public class Location implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "name", length = 100)
    String name;

    @Embedded
    Address address;

    @OneToMany(mappedBy = "location")
    List<Revenue> revenueList = new ArrayList<>();

    @OneToMany(mappedBy = "shippedFrom")
    List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    List<Stock> stockList = new ArrayList<>();

}
