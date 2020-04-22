package ro.msg.learning.controllers.utils;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(int id) {
        super("could not find order " + id);
    }

}
