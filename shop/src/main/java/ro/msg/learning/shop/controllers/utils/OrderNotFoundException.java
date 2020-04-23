package ro.msg.learning.shop.controllers.utils;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(int id) {
        super("could not find order " + id);
    }

}
