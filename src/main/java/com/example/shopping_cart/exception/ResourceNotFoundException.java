package com.example.shopping_cart.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " 不存在，ID: " + id);
    }
}
