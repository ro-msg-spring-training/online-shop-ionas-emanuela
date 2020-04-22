package ro.msg.learning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.controllers.utils.OrderNotFoundException;
import ro.msg.learning.controllers.utils.OrderResourceAssembler;
import ro.msg.learning.dtos.*;
import ro.msg.learning.entities.Order;
import ro.msg.learning.entities.OrderDetail;
import ro.msg.learning.repositories.OrderDetailRepository;
import ro.msg.learning.services.OrderService;

import javax.management.monitor.MonitorMBean;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

//Create a Rest Controller for the "Create order" operation,
// which should have a POST mapping accepting a JSON request
// body and producing a JSON response body.

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderResourceAssembler orderResourceAssembler;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/orders/{id}")
    public EntityModel<OrdersDTO> findById(@PathVariable int id) {

        OrdersDTO order = orderService.findById(id);

        if(null == order) {
            throw new OrderNotFoundException(id);
        }

        return orderResourceAssembler.toModel(order);

    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<OrdersDTO>> findAll() {

        List<EntityModel<OrdersDTO>> orderList = orderService.findAll().stream()
                .map(orderResourceAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(orderList,
                linkTo(methodOn(OrderController.class).findAll()).withSelfRel());

    }

    @PostMapping("/orders")
    public EntityModel<OrdersDTO> createOrder(@RequestBody OrderDTO orderDTO, CustomerDTO customerDTO, LocationDTO locationDTO) {

        Order order = orderService.createOrder(orderDTO, customerDTO, locationDTO);

        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for(OrderDetail orderDetail: orderDetailList) {
            productDTOList.add(new ProductDTO(orderDetail.getProduct(), orderDetail.getProduct().getCategory()));
        }

        OrdersDTO ordersDTO = new OrdersDTO(order, productDTOList);

        return orderResourceAssembler.toModel(ordersDTO);

    }

}
