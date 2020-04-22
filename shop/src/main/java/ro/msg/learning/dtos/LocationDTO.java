package ro.msg.learning.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.msg.learning.entities.Address;
import ro.msg.learning.entities.Location;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class LocationDTO {

    int id;
    String name;
    Address address;

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.address = location.getAddress();
    }

}
