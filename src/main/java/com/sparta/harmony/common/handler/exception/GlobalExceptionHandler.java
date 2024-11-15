package com.sparta.harmony.common.handler.exception;

import com.sparta.harmony.order.exception.OrderNotFoundException;
import com.sparta.harmony.order.exception.PaymentsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.sparta.harmony.order.controller"})
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestApiException> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RestApiException> nullPointerExceptionHandler(NullPointerException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<RestApiException> notFoundOrderExceptionHandler(OrderNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PaymentsNotFoundException.class)
    public ResponseEntity<RestApiException> notFoundPaymentsExceptionHandler(PaymentsNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiException> ArgumentNotValidException(MethodArgumentNotValidException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
