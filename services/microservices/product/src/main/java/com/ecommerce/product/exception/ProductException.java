package com.ecommerce.product.exception;

import com.ecommerce.product.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProductException {

    @ExceptionHandler
    public ResponseEntity<String> handleCustNoFountException(ProductNotFoundException ex){
       return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgException(MethodArgumentNotValidException exception){
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var field = ((FieldError)error).getField();
                    var errorMsg = error.getDefaultMessage();
                    errors.put(field,errorMsg);
                });
        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }
}
