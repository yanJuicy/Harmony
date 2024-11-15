package com.sparta.harmony.order.exception;

public class PaymentsNotFoundException extends RuntimeException {
    public PaymentsNotFoundException(String message) {
        super(message);
    }
}
