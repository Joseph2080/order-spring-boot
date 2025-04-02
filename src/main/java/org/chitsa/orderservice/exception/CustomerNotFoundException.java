package org.chitsa.orderservice.exception;

public class CustomerNotFoundException extends ModelNotFoundException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
