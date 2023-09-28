package com.user.userservices.customException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource Not Present in Database");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
