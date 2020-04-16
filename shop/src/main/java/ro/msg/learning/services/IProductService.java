package ro.msg.learning.services;

import ro.msg.learning.dtos.ProductDTO;

import java.util.List;

public interface IProductService {

    ProductDTO findById(int id);
    ProductDTO create();
    int update(ProductDTO productDTO);
    int delete(ProductDTO productDTO);
    List<ProductDTO> findAll();

}
