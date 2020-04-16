package ro.msg.learning.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.dtos.ProductDTO;
import ro.msg.learning.entities.Product;
import ro.msg.learning.repositories.ProductCategoryRepository;
import ro.msg.learning.repositories.ProductRepository;

import java.util.Iterator;
import java.util.List;

@Service
public class ProductService implements IProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductDTO findById(int id) {
        Product product = productRepository.findById(id).orElse(null);
        if(null == product) {
            return null;
        }

        return new ProductDTO(product, product.getProductCategory());
    }

    @Override
    public ProductDTO create() {
        return null;
    }

    @Override
    public int update(ProductDTO productDTO) {
        return 0;
    }

    @Override
    public int delete(ProductDTO productDTO) {
        return 0;
    }

    @Override
    public List<ProductDTO> findAll() {

        List<Product> productList = productRepository.findAll();

        Iterator<Product> product = productList.iterator();

        return null;
    }

}
