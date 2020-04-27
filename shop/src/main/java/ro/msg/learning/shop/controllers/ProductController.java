package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.services.ProductService;
import ro.msg.learning.shop.services.utils.EntityNotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> findAll() {
        List<Product> products = productService.findAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product: products) {
            productDTOS.add(new ProductDTO(product, product.getCategory()));
        }

        return productDTOS;
    }

    @GetMapping("/products/{id}")
    public ProductDTO findById(@PathVariable int id) {
        Product product = productService.findProductById(id);

        if(null == product){
            throw new EntityNotFoundException(id, "product");
        }

        return new ProductDTO(product, product.getCategory());
    }

    @PostMapping("/products")
    public ProductDTO saveProduct(@RequestBody ProductDTO toSave) {
        Product savedProduct = productService.saveProduct(toSave);

        return new ProductDTO(savedProduct, savedProduct.getCategory());
    }

    @PutMapping("/products/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO updatedProduct, @PathVariable int id) {

        updatedProduct.setId(id);

        Product product = productService.updateProduct(updatedProduct);

        return new ProductDTO(product, product.getCategory());
    }

    @Transactional
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        Product toDelete = productService.findProductById(id);

        if(null == toDelete) {
            throw new EntityNotFoundException(id, "product");
        }

        productService.deleteProductById(id);
    }

}
