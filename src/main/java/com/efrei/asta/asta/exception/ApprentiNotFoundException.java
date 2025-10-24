package com.efrei.asta.asta.exception;

public class ApprentiNotFoundException extends RuntimeException {
    public ApprentiNotFoundException(Integer id) {
        super("L'apprenti avec l'identifiant " + id + " est introuvable.");
    }
    
    public ApprentiNotFoundException(String message) {
        super(message);
    }
}

