package ro.msg.learning.shop.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.msg.learning.shop.entities.Customer;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@ToString(exclude = {"password"})
public class CustomerDTO {

    int id;
    String firstName;
    String lastName;
    String username;
    String password;
    String emailAddress;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.username = customer.getUsername();
        this.password = customer.getPassword();
        this.emailAddress = customer.getEmailAddress();
    }

}
