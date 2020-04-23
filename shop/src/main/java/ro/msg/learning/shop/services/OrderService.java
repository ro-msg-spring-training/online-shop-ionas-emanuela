package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.utils.OrderNotCompletedException;
import ro.msg.learning.shop.services.utils.StrategyFactory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final StrategyFactory strategy;

    @Transactional
    @Override
    public Order createOrder(OrderInfoDTO orderInfoDTO) {

        List<RestockDTO> restockDTOList = strategy.getStrategy().findLocations(orderInfoDTO);

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

        List<OrderDetail>orderDetailList = new ArrayList<>();

        Order order = Order.builder().address(orderInfoDTO.getDeliveryAddress())
                .createdAt(orderInfoDTO.getTimestamp())
                .customer(customer)
                .shippedFrom(location).build();

        Order savedOrder = orderRepository.save(order);

        Iterator it = orderInfoDTO.getProducts().entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int productId = (int) pair.getKey();
            int quantity = (int) pair.getValue();

            Product product = productRepository.findById(productId).orElse(null);

            if(null == product) {
                throw new OrderNotCompletedException("no product with id " + productId);
            }

            OrderDetailKey orderDetailKey = OrderDetailKey.builder().orderId(savedOrder.getId()).productId(product.getId()).build();
            OrderDetail orderDetail = OrderDetail.builder().id(orderDetailKey).order(savedOrder).product(product).quantity(quantity).build();

            orderDetailRepository.save(orderDetail);

        }

        return orderRepository.findById(savedOrder.getId()).orElse(null);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

}
