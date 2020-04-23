package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.utils.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Override
    public Product findProductById(int id) {

        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(ProductDTO toSave) {

        System.out.println(toSave.getCategory());

        ProductCategory  productCategory = productCategoryRepository.findByName(toSave.getCategory());

        if(null == productCategory){
            throw new EntityNotFoundException(toSave.getCategory(), "product category");
        }

        Product product = Product.builder().name(toSave.getName())
                .description(toSave.getDescription())
                .price(toSave.getPrice())
                .weight(toSave.getWeight())
                .imageUrl(toSave.getImageUrl())
                .category(productCategory).build();

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDTO updatedProduct) {

        Product toUpdate = productRepository.findById(updatedProduct.getId()).orElse(null);

        if(toUpdate == null) {
            throw new EntityNotFoundException(updatedProduct.getId(), "product");
        }

        if(updatedProduct.getCategory().equals("")) {
            updatedProduct.setCategory(toUpdate.getCategory().getName());
        }
        if(updatedProduct.getDescription().equals("")) {
            updatedProduct.setDescription(toUpdate.getDescription());
        }
        if(updatedProduct.getName().equals("")) {
            updatedProduct.setName(toUpdate.getName());
        }
        if(updatedProduct.getPrice().equals(new BigDecimal(0))) {
            updatedProduct.setPrice(toUpdate.getPrice());
        }
        if(updatedProduct.getWeight().equals((double) 0)) {
            updatedProduct.setWeight(toUpdate.getWeight());
        }
        if(updatedProduct.getImageUrl().equals("")) {
            updatedProduct.setImageUrl(toUpdate.getImageUrl());
        }

        ProductCategory productCategory = productCategoryRepository.findByName(updatedProduct.getCategory());

        Product product = Product.builder().id(updatedProduct.getId()).name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .price(updatedProduct.getPrice())
                .weight(updatedProduct.getWeight())
                .imageUrl(updatedProduct.getImageUrl())
                .category(productCategory).build();

        return productRepository.save(product);

    }

    @Override
    public void deleteProductById(int id) {

        orderDetailRepository.deleteByProductId(id);
        stockRepository.deleteByProductId(id);
        productRepository.deleteById(id);

    }

    @Override
    public List<Product> findAllProducts() {

        return productRepository.findAll();

    }

    @Override
    public List<Product> findAllProductsByOrderId(int id) {

        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(id);
        List<Product> products = new ArrayList<>();

        for(OrderDetail orderDetail: orderDetails) {
            products.add(orderDetail.getProduct());
        }

        return products;
    }

}
