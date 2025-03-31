package org.chitsa.orderservice.exception;

public class OrderNotFoundException extends ModelNotFoundException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}
