package ro.msg.learning.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Embeddable
public class Address {

    @Column(name = "address_country", length = 100)
    String addressCountry;

    @Column(name = "address_city", length = 100)
    String addressCity;

    @Column(name = "address_county", length = 100)
    String addressCounty;

    @Column(name = "address_street_address", length = 100)
    String addressStreetAddress;
}
