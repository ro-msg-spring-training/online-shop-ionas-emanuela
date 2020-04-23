package ro.msg.learning.shop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Embeddable
public class Address {

    @Column(name = "address_country", length = 100)
    String country;

    @Column(name = "address_city", length = 100)
    String city;

    @Column(name = "address_county", length = 100)
    String county;

    @Column(name = "address_street_address", length = 100)
    String streetAddress;
}
