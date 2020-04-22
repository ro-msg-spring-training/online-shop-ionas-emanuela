package ro.msg.learning.services.utils;

public class OrderNotCompletedException extends RuntimeException {

    public OrderNotCompletedException() {
        super("could not complete order");
    }

}
