package ro.msg.learning.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "orders", schema = "shop_schema")
@Entity
public class Order implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "created_at")
    Date createdAt;

    @Embedded
    Address address;

    @ManyToOne
    @JoinColumn(name = "shipped_from")
    Location shippedFrom;

    @ManyToOne
    @JoinColumn(name = "customer")
    Customer customer;

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetailList = new ArrayList<>();

}
