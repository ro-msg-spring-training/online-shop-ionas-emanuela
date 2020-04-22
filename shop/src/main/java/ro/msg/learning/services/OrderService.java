package ro.msg.learning.services;

import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.dtos.*;
import ro.msg.learning.entities.*;
import ro.msg.learning.repositories.*;
import ro.msg.learning.services.utils.IStrategy;
import ro.msg.learning.services.utils.OrderNotCompletedException;
import ro.msg.learning.services.utils.StrategyFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class OrderService implements IOrderService{

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StrategyFactory strategy;

    @Override
    public Order createOrder(OrderDTO orderDTO, CustomerDTO customerDTO, LocationDTO locationDTO) {

        List<RestockDTO> restockDTOList;
        try{
            restockDTOList = strategy.getStrategy().findLocations(orderDTO);

        }catch (OrderNotCompletedException ex) {
            throw new OrderNotCompletedException();
        }

        for(RestockDTO restockDTO: restockDTOList) {

            Stock stock = stockRepository.findByProductAndLocation(Product.builder().id(restockDTO.getProduct().getId()).build()
                    , Location.builder().id(restockDTO.getLocation().getId()).build());

            if(null == stock) {
                throw new OrderNotCompletedException();
            }

            stock.setQuantity(stock.getQuantity() - restockDTO.getQuantity());
            stockRepository.save(stock);

        }

        Customer customer = customerRepository.findById(customerDTO.getId()).orElse(null);
        Location location = locationRepository.findById(locationDTO.getId()).orElse(null);

        List<OrderDetail>orderDetailList = new ArrayList<>();

        Order order = Order.builder().address(orderDTO.getDeliveryAddress())
                .createdAt(orderDTO.getTimestamp())
                .customer(customer)
                .shippedFrom(location).build();

        Order savedOrder = orderRepository.save(order);

        Iterator it = orderDTO.getProducts().entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int productId = (int) pair.getKey();
            int quantity = (int) pair.getValue();

            Product product = productRepository.findById(productId).orElse(null);

            if(null == product) {
                throw new OrderNotCompletedException();
            }

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(savedOrder).product(product).quantity(quantity).build();

            orderDetailRepository.save(orderDetail);

        }

        return orderRepository.findById(savedOrder.getId()).orElse(null);
    }

    public List<OrdersDTO> findAll() {

        List<Order> orderList;
        List<OrdersDTO> ordersDTOList = new ArrayList<>();

        try{
           orderList = orderRepository.findAll();

           for(Order order: orderList) {

               List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
               List<ProductDTO> productDTOList = new ArrayList<>();

               for(OrderDetail orderDetail: orderDetailList) {
                   productDTOList.add(new ProductDTO(orderDetail.getProduct(), orderDetail.getProduct().getCategory()));
               }

               ordersDTOList.add(new OrdersDTO(order, productDTOList));
           }

        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return ordersDTOList;
    }

    public OrdersDTO findById(int id) {

        Order order = orderRepository.findById(id).orElse(null);

        if(null == order) {
            return null;
        }

        System.out.println(order.getId());

        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for(OrderDetail orderDetail: orderDetailList) {
            productDTOList.add(new ProductDTO(orderDetail.getProduct(), orderDetail.getProduct().getCategory()));
        }

        return new OrdersDTO(order, productDTOList);
    }

}
