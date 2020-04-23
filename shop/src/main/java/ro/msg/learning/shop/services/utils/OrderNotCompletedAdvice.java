package ro.msg.learning.shop.services.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderNotCompletedAdvice {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    String orderNotCompletedHandler(OrderNotCompletedException ex) {
        return ex.getMessage();
    }

}
