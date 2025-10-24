package com.efrei.asta.asta.service;

import com.efrei.asta.asta.model.Apprenti;
import com.efrei.asta.asta.repository.ApprentiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RechercheService {
    
    private final ApprentiRepository apprentiRepository;
    
    public RechercheService(ApprentiRepository apprentiRepository) {
        this.apprentiRepository = apprentiRepository;
    }
    
    /**
     * Recherche des apprentis par nom (nom ou prénom)
     */
    public List<Apprenti> rechercherParNom(String nom) {
        return apprentiRepository.rechercherParNom(nom);
    }
    
    /**
     * Recherche des apprentis par raison sociale de l'entreprise
     */
    public List<Apprenti> rechercherParEntreprise(String entreprise) {
        return apprentiRepository.rechercherParEntreprise(entreprise);
    }
    
    /**
     * Recherche des apprentis par mot-clé de la mission
     */
    public List<Apprenti> rechercherParMotCleMission(String motCle) {
        return apprentiRepository.rechercherParMotCleMission(motCle);
    }
    
    /**
     * Recherche des apprentis par année académique
     */
    public List<Apprenti> rechercherParAnneeAcademique(String anneeAcademique) {
        return apprentiRepository.rechercherParAnneeAcademique(anneeAcademique);
    }
    
    /**
     * Recherche multi-critères
     */
    public List<Apprenti> rechercher(String critere, String valeur) {
        return switch (critere) {
            case "nom" -> rechercherParNom(valeur);
            case "entreprise" -> rechercherParEntreprise(valeur);
            case "motCle" -> rechercherParMotCleMission(valeur);
            case "anneeAcademique" -> rechercherParAnneeAcademique(valeur);
            default -> List.of();
        };
    }
}

