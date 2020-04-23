package ro.msg.learning.shop.dtos;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
@Data
public class ProductDTO {

    int id;
    String name;
    String description;
    BigDecimal price;
    Double weight;
    String imageUrl;
    String category;
    String categoryDescription;

    public ProductDTO(Product product, ProductCategory productCategory) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.weight = product.getWeight();
        this.imageUrl = product.getImageUrl();
        this.category = productCategory.getName();
        this.categoryDescription = productCategory.getDescription();
    }

}
