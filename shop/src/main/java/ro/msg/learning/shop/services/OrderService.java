package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.OrderInfoDTO;
import ro.msg.learning.shop.dtos.RestockDTO;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.utils.IStrategy;
import ro.msg.learning.shop.services.utils.OrderNotCompletedException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService{

    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    private final IStrategy getStrategy;

    @Transactional
    public Order createOrder(OrderInfoDTO orderInfoDTO) {

        List<RestockDTO> restockDTOList = getStrategy.findLocations(orderInfoDTO);

        for(RestockDTO restockDTO: restockDTOList) {

            Product product = productRepository.findById(restockDTO.getProduct().getId()).orElse(null);
            Location location = locationRepository.findById(restockDTO.getLocation().getId()).orElse(null);
            Stock stock = stockRepository.findByProductAndLocation(product, location);

            if(null == stock) {
                throw new OrderNotCompletedException("null stock");
            }

            stock.setQuantity(stock.getQuantity() - restockDTO.getQuantity());
            stockRepository.save(stock);

        }

        Customer customer = customerRepository.findById(1).orElse(null);
        Location location = locationRepository.findById(orderInfoDTO.getShopAddress().getId()).orElse(null);

        Order order = Order.builder().address(orderInfoDTO.getDeliveryAddress())
                .createdAt(orderInfoDTO.getTimestamp())
                .customer(customer)
                .shippedFrom(location).build();

        Order savedOrder = orderRepository.save(order);

        orderInfoDTO.getProducts().forEach((productId, quantity) -> {
            Product product = productRepository.findById(productId).orElse(null);

            if(null == product) {
                throw new OrderNotCompletedException("no product with id " + productId);
            }

            OrderDetailKey orderDetailKey = OrderDetailKey.builder().orderId(savedOrder.getId()).productId(product.getId()).build();
            OrderDetail orderDetail = OrderDetail.builder().id(orderDetailKey).order(savedOrder).product(product).quantity(quantity).build();

            orderDetailRepository.save(orderDetail);
        });

        return orderRepository.findById(savedOrder.getId()).orElse(null);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

}
