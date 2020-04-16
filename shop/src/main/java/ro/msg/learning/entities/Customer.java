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
@Table(name = "customer")
@Entity
public class Customer implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "first_name", length = 100)
    String firstName;

    @Column(name = "last_name", length = 100)
    String lastName;

    @Column(name = "username", length = 100)
    String username;

    @Column(name = "password", length = 100)
    String password;

    @Column(name = "email_address", length = 100)
    String emailAddress;

    @OneToMany(mappedBy = "customer")
    List<Order> orderList = new ArrayList<>();
}
