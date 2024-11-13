package com.sparta.harmony.order.handler.exception;

public class PaymentsNotFoundException extends RuntimeException {
    public PaymentsNotFoundException(String message) {
        super(message);
    }
}
