package com.unistar.orders.exceptions;

public class OrderServiceException extends RuntimeException {

    public OrderServiceException(String message) {
        super(message);
    }
}
