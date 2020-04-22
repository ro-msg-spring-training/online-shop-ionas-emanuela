package ro.msg.learning.services.utils;

public class ProductNotFoundException extends RuntimeException {


    public ProductNotFoundException(int id) {
        super("could not find product " + id);
    }


}
