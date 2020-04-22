package ro.msg.learning.controllers.utils;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ro.msg.learning.controllers.OrderController;
import ro.msg.learning.dtos.OrdersDTO;
import ro.msg.learning.entities.Order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderResourceAssembler implements RepresentationModelAssembler<OrdersDTO, EntityModel<OrdersDTO>> {

    @Override
    public EntityModel<OrdersDTO> toModel(OrdersDTO entity) {

        return new EntityModel<>(entity,
                linkTo(methodOn(OrderController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAll()).withRel("orders"));

    }
}
