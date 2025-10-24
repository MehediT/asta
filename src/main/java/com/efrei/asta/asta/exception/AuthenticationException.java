package com.efrei.asta.asta.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Les identifiants de connexion sont invalides. Veuillez vérifier votre login et mot de passe.");
    }
    
    public AuthenticationException(String message) {
        super(message);
    }
}

