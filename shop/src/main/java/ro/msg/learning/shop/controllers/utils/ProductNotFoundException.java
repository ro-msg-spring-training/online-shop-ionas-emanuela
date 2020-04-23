package ro.msg.learning.shop.controllers.utils;

public class ProductNotFoundException extends RuntimeException {


    public ProductNotFoundException(int id) {
        super("could not find product " + id);
    }


}
