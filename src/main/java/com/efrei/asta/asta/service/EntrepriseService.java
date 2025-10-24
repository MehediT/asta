package com.efrei.asta.asta.service;

import com.efrei.asta.asta.exception.InvalidDataException;
import com.efrei.asta.asta.model.Entreprise;
import com.efrei.asta.asta.repository.EntrepriseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {
    
    private final EntrepriseRepository entrepriseRepository;
    
    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }
    
    public List<Entreprise> findAll() {
        return entrepriseRepository.findAll();
    }
    
    public Optional<Entreprise> findById(Integer id) {
        return entrepriseRepository.findById(id);
    }
    
    @Transactional
    public Entreprise save(Entreprise entreprise) {
        validerEntreprise(entreprise);
        return entrepriseRepository.save(entreprise);
    }
    
    @Transactional
    public void delete(Integer id) {
        entrepriseRepository.deleteById(id);
    }
    
    public void validerEntreprise(Entreprise entreprise) {
        if (entreprise == null) {
            return;
        }
        
        InvalidDataException exception = new InvalidDataException("Certains champs de l'entreprise sont invalides");
        boolean hasError = false;
        
        if (entreprise.getRaisonSociale() != null && !entreprise.getRaisonSociale().trim().isEmpty()) {
            if (entreprise.getRaisonSociale().trim().length() < 2) {
                exception.addFieldError("entreprise.raisonSociale", "La raison sociale doit contenir au moins 2 caractères");
                hasError = true;
            }
        }
        
        if (hasError) {
            throw exception;
        }
    }
}

