package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.utils.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StockRepository stockRepository;
    private final SupplierRepository supplierRepository;

    public Product findProductById(int id) {

        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(ProductDTO toSave) {

        ProductCategory  productCategory = productCategoryRepository.findByName(toSave.getCategory());

        if(null == productCategory){
            throw new EntityNotFoundException(toSave.getCategory(), "product category");
        }

        Supplier supplier = supplierRepository.findByName(toSave.getSupplier());

        if(null == supplier) {
            throw new EntityNotFoundException(toSave.getSupplier(), "supplier");
        }

        Product product = Product.builder().name(toSave.getName())
                .description(toSave.getDescription())
                .price(toSave.getPrice())
                .weight(toSave.getWeight())
                .imageUrl(toSave.getImageUrl())
                .category(productCategory)
                .supplier(supplier).build();

        return productRepository.save(product);
    }

    public Product updateProduct(ProductDTO updatedProduct) {

        Product toUpdate = productRepository.findById(updatedProduct.getId()).orElse(null);

        if(null == toUpdate) {
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
        if(updatedProduct.getSupplier().equals("")) {
            updatedProduct.setSupplier(toUpdate.getSupplier().getName());
        }

        ProductCategory productCategory = productCategoryRepository.findByName(updatedProduct.getCategory());

        if(null == productCategory) {
            throw new EntityNotFoundException(updatedProduct.getCategory(), "product category");
        }

        Supplier supplier = supplierRepository.findByName(updatedProduct.getSupplier());

        if(null == supplier) {
            throw new EntityNotFoundException(updatedProduct.getSupplier(), "supplier");
        }

        Product product = Product.builder().id(updatedProduct.getId()).name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .price(updatedProduct.getPrice())
                .weight(updatedProduct.getWeight())
                .imageUrl(updatedProduct.getImageUrl())
                .category(productCategory)
                .supplier(supplier).build();

        return productRepository.save(product);

    }

    public void deleteProductById(int id) {

        orderDetailRepository.deleteByProductId(id);
        stockRepository.deleteByProductId(id);
        productRepository.deleteById(id);

    }

    public List<Product> findAllProducts() {

        return productRepository.findAll();

    }

    public Map<Product, Integer> findAllProductsByOrderId(int id) {

        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(id);

        Map<Product, Integer> productQuantity = new HashMap<>();

        for(OrderDetail orderDetail: orderDetails) {
            productQuantity.put(orderDetail.getProduct(), orderDetail.getQuantity());
        }

        return productQuantity;
    }

}
