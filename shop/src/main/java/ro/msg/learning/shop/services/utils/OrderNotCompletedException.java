package ro.msg.learning.shop.services.utils;

public class OrderNotCompletedException extends RuntimeException {

    public OrderNotCompletedException(String message) {
        super("could not complete order due to " + message);
    }

}
