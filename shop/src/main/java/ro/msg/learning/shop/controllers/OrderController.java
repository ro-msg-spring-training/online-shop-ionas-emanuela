package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.controllers.utils.OrderNotFoundException;
import ro.msg.learning.shop.dtos.OrderDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.services.OrderService;
import ro.msg.learning.shop.services.ProductService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/orders/{id}")
    public OrderDTO findOrderById(@PathVariable int id) {

        Order order = orderService.findOrderById(id);

        if(null == order) {
            throw new OrderNotFoundException(id);
        }

        List<Product> products = productService.findAllProductsByOrderId(order.getId());
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product: products) {
            productDTOS.add(new ProductDTO(product, product.getCategory()));
        }

        return new OrderDTO(order, productDTOS);

    }

    @GetMapping("/orders")
    public List<OrderDTO> findAllOrders() {

        List<Order> orders = orderService.findAllOrders();
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for(Order order: orders) {
            List<Product> products = productService.findAllProductsByOrderId(order.getId());
            List<ProductDTO> productDTOS = new ArrayList<>();

            for(Product product: products) {
                productDTOS.add(new ProductDTO(product, product.getCategory()));
            }

            orderDTOS.add(new OrderDTO(order, productDTOS));
        }

        return orderDTOS;
    }

    @PostMapping("/orders")
    public OrderDTO createOrder(@RequestBody OrderInfoDTO orderInfoDTO) {

        Order order = orderService.createOrder(orderInfoDTO);

        List<Product> products = productService.findAllProductsByOrderId(order.getId());
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product: products) {
            productDTOS.add(new ProductDTO(product, product.getCategory()));
        }

        return new OrderDTO(order, productDTOS);

    }

}
