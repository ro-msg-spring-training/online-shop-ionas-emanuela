package ro.msg.learning.services.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.msg.learning.controllers.ProductController;
import ro.msg.learning.dtos.ProductDTO;

@Component
public class ProductResourceAssembler implements RepresentationModelAssembler<ProductDTO, EntityModel<ProductDTO>> {

    @Override
    public EntityModel<ProductDTO> toModel(ProductDTO entity) {

        return new EntityModel<>(entity,
                linkTo(methodOn(ProductController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).findAll()).withRel("products"));

    }

}
