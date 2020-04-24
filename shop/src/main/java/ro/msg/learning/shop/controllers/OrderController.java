package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dtos.OrderDTO;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.services.OrderService;
import ro.msg.learning.shop.services.ProductService;
import ro.msg.learning.shop.services.utils.EntityNotFoundException;

import java.util.*;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/orders/{id}")
    public OrderDTO findOrderById(@PathVariable int id) {

        Order order = orderService.findOrderById(id);

        if(null == order) {
            throw new EntityNotFoundException(id, "order");
        }

        return convertOrder(order);

    }

    @GetMapping("/orders")
    public List<OrderDTO> findAllOrders() {

        List<Order> orders = orderService.findAllOrders();
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for(Order order: orders) {
            orderDTOS.add(convertOrder(order));
        }

        return orderDTOS;
    }

    @PostMapping("/orders")
    public OrderDTO createOrder(@RequestBody OrderInfoDTO orderInfoDTO) {

        Order order = orderService.createOrder(orderInfoDTO);

        return convertOrder(order);

    }

    private OrderDTO convertOrder(Order order) {

        Map<Product, Integer> products = productService.findAllProductsByOrderId(order.getId());
        Map<ProductDTO, Integer> productDTOS = new HashMap<>();

        Iterator it = products.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Product product = (Product) pair.getKey();
            Integer quantity = (Integer) pair.getValue();

            productDTOS.put(new ProductDTO(product, product.getCategory()), quantity);

        }

        return new OrderDTO(order, productDTOS);
    }

}
