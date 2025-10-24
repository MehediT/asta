package com.efrei.asta.asta.service;

import com.efrei.asta.asta.exception.AuthenticationException;
import com.efrei.asta.asta.model.TuteurEnseignant;
import com.efrei.asta.asta.repository.TuteurEnseignantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    
    private final TuteurEnseignantRepository tuteurEnseignantRepository;
    
    public AuthenticationService(TuteurEnseignantRepository tuteurEnseignantRepository) {
        this.tuteurEnseignantRepository = tuteurEnseignantRepository;
    }
    
    public TuteurEnseignant authenticate(String login, String motDePasse) {
        Optional<TuteurEnseignant> tuteur = tuteurEnseignantRepository.findByLogin(login);
        
        if (tuteur.isEmpty()) {
            throw new AuthenticationException();
        }
        
        TuteurEnseignant tuteurEnseignant = tuteur.get();
        
        // Vérification simple du mot de passe (en production, utiliser BCrypt)
        if (!tuteurEnseignant.getMotDePasse().equals(motDePasse)) {
            throw new AuthenticationException();
        }
        
        return tuteurEnseignant;
    }
    
    public TuteurEnseignant findById(Integer id) {
        return tuteurEnseignantRepository.findById(id).orElse(null);
    }
}

