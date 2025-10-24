package com.efrei.asta.asta.exception;

import java.util.HashMap;
import java.util.Map;

public class InvalidDataException extends RuntimeException {
    private Map<String, String> fieldErrors = new HashMap<>();
    
    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }
    
    public InvalidDataException(String field, String error) {
        super("Données invalides");
        this.fieldErrors.put(field, error);
    }
    
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
    
    public void addFieldError(String field, String error) {
        this.fieldErrors.put(field, error);
    }
}

