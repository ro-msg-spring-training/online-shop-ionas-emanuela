package ro.msg.learning.services;

import ro.msg.learning.dtos.ProductDTO;
import ro.msg.learning.entities.Product;

import java.util.List;

public interface IProductService {

    ProductDTO findById(int id);
    ProductDTO create();
    ProductDTO save(ProductDTO productDTO);
    ProductDTO update(ProductDTO oldProductDTO, ProductDTO newProductDTO);
    void delete(ProductDTO productDTO);
    List<ProductDTO> findAll();

}
