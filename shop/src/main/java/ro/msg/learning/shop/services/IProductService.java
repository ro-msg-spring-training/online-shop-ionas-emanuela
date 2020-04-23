package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.entities.Product;

import java.util.List;

public interface IProductService {

    Product findProductById(int id);
    Product saveProduct(ProductDTO toSave);
    Product updateProduct(ProductDTO updatedProduct);
    void deleteProductById(int id);
    List<Product> findAllProducts();
    List<Product> findAllProductsByOrderId(int id);

}
