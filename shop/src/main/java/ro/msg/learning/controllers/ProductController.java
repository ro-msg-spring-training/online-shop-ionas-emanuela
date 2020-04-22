package ro.msg.learning.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.dtos.ProductDTO;
import ro.msg.learning.services.ProductService;
import ro.msg.learning.controllers.utils.ProductNotFoundException;
import ro.msg.learning.controllers.utils.ProductResourceAssembler;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductResourceAssembler productResourceAssembler;

    @GetMapping("/products")
    public CollectionModel<EntityModel<ProductDTO>> findAll() {
        List<EntityModel<ProductDTO>> productDTOList = productService.findAll().stream()
                .map(productResourceAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(productDTOList,
                linkTo(methodOn(ProductController.class).findAll()).withSelfRel());

    }

    @GetMapping("/products/{id}")
    public EntityModel<ProductDTO> findById(@PathVariable int id) {
        ProductDTO productDTO = productService.findById(id);

        if(null == productDTO){
            throw new ProductNotFoundException(id);
        }

        return productResourceAssembler.toModel(productDTO);
    }

    @PostMapping("/products")
    public EntityModel<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO productDTO1 = productService.save(productDTO);

        return productResourceAssembler.toModel(productDTO1);
    }

    @PutMapping("/products/{id}")
    public EntityModel<ProductDTO> updateProduct(@RequestBody ProductDTO newProductDTO, @PathVariable int id) {

        ProductDTO oldProductDTO = productService.findById(id);
        ProductDTO productDTO = productService.update(oldProductDTO, newProductDTO);

        return productResourceAssembler.toModel(productDTO);
    }

    @Transactional
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        ProductDTO productDTO = productService.findById(id);

        productService.delete(id);
    }

}
