package ro.msg.learning.shop.entities;

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
public class Location {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "name", length = 100)
    String name;

    @Embedded
    Address address;

    @OneToMany(mappedBy = "location")
    List<Revenue> revenueList = new ArrayList<>();

    //@OneToMany(mappedBy = "shippedFrom")
    //List<Order> orderList = new ArrayList<>();

    //@OneToMany(mappedBy = "location")
    //List<Stock> stockList = new ArrayList<>();

    @Override
    public String toString() {
        return name + address.toString();
    }

}
