package ro.msg.learning.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.dtos.ProductDTO;
import ro.msg.learning.entities.*;
import ro.msg.learning.repositories.OrderDetailRepository;
import ro.msg.learning.repositories.ProductCategoryRepository;
import ro.msg.learning.repositories.ProductRepository;
import ro.msg.learning.repositories.StockRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductService implements IProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    StockRepository stockRepository;

    @Override
    public ProductDTO findById(int id) {
        Product product = productRepository.findById(id).orElse(null);
        if(null == product) {
            return null;
        }

        return new ProductDTO(product, product.getCategory());
    }

    @Override
    public ProductDTO create() {
        return null;
    }

    @Override
    public ProductDTO save (ProductDTO productDTO) {

        System.out.println(productDTO.getCategory());

        ProductCategory productCategory = ProductCategory.builder().id(1).build();

        try{
            productCategory = productCategoryRepository.findByName(productDTO.getCategory());
            System.out.println(productCategory);

        }catch (NullPointerException npe) {
            System.out.println("category does not exist");
        }

        Product product = Product.builder().name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .imageUrl(productDTO.getImageUrl())
                .category(productCategory).build();

        Product savedProduct = productRepository.save(product);

        return new ProductDTO(savedProduct, productCategory);
    }

    @Override
    public ProductDTO update(ProductDTO oldProductDTO, ProductDTO newProductDTO) {

        if(null == oldProductDTO || null == newProductDTO) {
            return null;
        }

        newProductDTO.setId(oldProductDTO.getId());
        if(newProductDTO.getCategory().equals("")) {
            newProductDTO.setCategory(oldProductDTO.getCategory());
        }
        if(newProductDTO.getDescription().equals("")) {
            newProductDTO.setDescription(oldProductDTO.getDescription());
        }
        if(newProductDTO.getName().equals("")) {
            newProductDTO.setName(oldProductDTO.getName());
        }
        if(newProductDTO.getPrice().equals(new BigDecimal(0))) {
            newProductDTO.setPrice(oldProductDTO.getPrice());
        }
        if(newProductDTO.getWeight().equals(0.0)) {
            newProductDTO.setWeight(oldProductDTO.getWeight());
        }
        if(newProductDTO.getImageUrl().equals("")) {
            newProductDTO.setImageUrl(oldProductDTO.getImageUrl());
        }

        ProductCategory productCategory = productCategoryRepository.findByName(newProductDTO.getCategory());

        Product product = Product.builder().id(newProductDTO.getId()).name(newProductDTO.getName())
                .description(newProductDTO.getDescription())
                .price(newProductDTO.getPrice())
                .imageUrl(newProductDTO.getImageUrl())
                .category(productCategory).build();

        Product updatedProduct = productRepository.save(product);

        return new ProductDTO(updatedProduct, updatedProduct.getCategory());
    }

    @Override
    public void delete(int id) {

        orderDetailRepository.deleteByProductId(id);
        stockRepository.deleteByProductId(id);
        productRepository.deleteById(id);

    }

    @Override
    public List<ProductDTO> findAll() {

        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> productList;

        try{
            productList = productRepository.findAll();
            for(Product product: productList) {
                productDTOList.add(new ProductDTO(product, product.getCategory()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return productDTOList;
    }

}
