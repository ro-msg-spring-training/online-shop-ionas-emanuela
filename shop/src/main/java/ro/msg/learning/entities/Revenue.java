package ro.msg.learning.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "revenue", schema = "shop_schema")
@Entity
public class Revenue implements IColumn{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "date")
    Date date;

    @Column(name = "sum")
    BigDecimal sum;

    @ManyToOne
    @JoinColumn(name = "location")
    Location location;

}
