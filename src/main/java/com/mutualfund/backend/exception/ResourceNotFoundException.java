package com.mutualfund.backend.exception;

// Handles global exceptions across the application
// Returns proper error responses to client
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}