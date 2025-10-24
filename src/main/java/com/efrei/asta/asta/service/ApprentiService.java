package com.efrei.asta.asta.service;

import com.efrei.asta.asta.exception.ApprentiNotFoundException;
import com.efrei.asta.asta.exception.InvalidDataException;
import com.efrei.asta.asta.model.Apprenti;
import com.efrei.asta.asta.repository.ApprentiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {
    
    private final ApprentiRepository apprentiRepository;
    
    public ApprentiService(ApprentiRepository apprentiRepository) {
        this.apprentiRepository = apprentiRepository;
    }
    
    public List<Apprenti> findAll() {
        return apprentiRepository.findAll();
    }
    
    public List<Apprenti> findAllNonArchives() {
        return apprentiRepository.findByArchiveFalse();
    }
    
    public List<Apprenti> findByTuteurAndAnneeAcademique(Integer tuteurId, String anneeAcademique) {
        return apprentiRepository.findByTuteurEnseignantIdAndAnneeAcademique(tuteurId, anneeAcademique);
    }
    
    public Optional<Apprenti> findById(Integer id) {
        return apprentiRepository.findById(id);
    }
    
    public Apprenti findByIdOrThrow(Integer id) {
        return apprentiRepository.findById(id)
            .orElseThrow(() -> new ApprentiNotFoundException(id));
    }
    
    @Transactional
    public Apprenti save(Apprenti apprenti) {
        validerApprenti(apprenti);
        return apprentiRepository.save(apprenti);
    }
    
    @Transactional
    public Apprenti update(Integer id, Apprenti apprentiModifie) {
        Apprenti apprentiExistant = findByIdOrThrow(id);
        
        validerApprenti(apprentiModifie);
        
        // Mise à jour des champs
        apprentiExistant.setProgramme(apprentiModifie.getProgramme());
        apprentiExistant.setAnneeAcademique(apprentiModifie.getAnneeAcademique());
        apprentiExistant.setMajeure(apprentiModifie.getMajeure());
        apprentiExistant.setNiveau(apprentiModifie.getNiveau());
        apprentiExistant.setNom(apprentiModifie.getNom());
        apprentiExistant.setPrenom(apprentiModifie.getPrenom());
        apprentiExistant.setEmail(apprentiModifie.getEmail());
        apprentiExistant.setTelephone(apprentiModifie.getTelephone());
        apprentiExistant.setFeedbackTuteur(apprentiModifie.getFeedbackTuteur());
        
        // Mise à jour des relations
        apprentiExistant.setEntreprise(apprentiModifie.getEntreprise());
        apprentiExistant.setMaitreApprentissage(apprentiModifie.getMaitreApprentissage());
        apprentiExistant.setMission(apprentiModifie.getMission());
        apprentiExistant.setVisite(apprentiModifie.getVisite());
        apprentiExistant.setEvaluation(apprentiModifie.getEvaluation());
        
        return apprentiRepository.save(apprentiExistant);
    }
    
    @Transactional
    public void delete(Integer id) {
        Apprenti apprenti = findByIdOrThrow(id);
        apprentiRepository.delete(apprenti);
    }
    
    // Méthodes de recherche
    public List<Apprenti> rechercherParNom(String nom) {
        return apprentiRepository.rechercherParNom(nom);
    }
    
    public List<Apprenti> rechercherParEntreprise(String entreprise) {
        return apprentiRepository.rechercherParEntreprise(entreprise);
    }
    
    public List<Apprenti> rechercherParMotCleMission(String motCle) {
        return apprentiRepository.rechercherParMotCleMission(motCle);
    }
    
    public List<Apprenti> rechercherParAnneeAcademique(String anneeAcademique) {
        return apprentiRepository.rechercherParAnneeAcademique(anneeAcademique);
    }
    
    // Gestion de la nouvelle année académique
    @Transactional
    public void creerNouvelleAnneeAcademique(String nouvelleAnnee) {
        List<Apprenti> tousLesApprentis = apprentiRepository.findByArchiveFalse();
        
        for (Apprenti apprenti : tousLesApprentis) {
            String niveauActuel = apprenti.getNiveau();
            
            if ("I3".equalsIgnoreCase(niveauActuel)) {
                // Archiver les I3
                apprenti.setArchive(true);
            } else if ("I2".equalsIgnoreCase(niveauActuel)) {
                // Promouvoir I2 vers I3
                apprenti.setNiveau("I3");
                apprenti.setAnneeAcademique(nouvelleAnnee);
            } else if ("I1".equalsIgnoreCase(niveauActuel)) {
                // Promouvoir I1 vers I2
                apprenti.setNiveau("I2");
                apprenti.setAnneeAcademique(nouvelleAnnee);
            }
            
            apprentiRepository.save(apprenti);
        }
    }
    
    private void validerApprenti(Apprenti apprenti) {
        InvalidDataException exception = new InvalidDataException("Certains champs sont invalides");
        boolean hasError = false;
        
        if (apprenti.getNom() == null || apprenti.getNom().trim().isEmpty()) {
            exception.addFieldError("nom", "Le nom est obligatoire");
            hasError = true;
        }
        
        if (apprenti.getPrenom() == null || apprenti.getPrenom().trim().isEmpty()) {
            exception.addFieldError("prenom", "Le prénom est obligatoire");
            hasError = true;
        }
        
        if (apprenti.getProgramme() == null || apprenti.getProgramme().trim().isEmpty()) {
            exception.addFieldError("programme", "Le programme est obligatoire");
            hasError = true;
        }
        
        if (apprenti.getAnneeAcademique() == null || apprenti.getAnneeAcademique().trim().isEmpty()) {
            exception.addFieldError("anneeAcademique", "L'année académique est obligatoire");
            hasError = true;
        }
        
        if (apprenti.getTelephone() != null && !apprenti.getTelephone().isEmpty()) {
            String tel = apprenti.getTelephone().replaceAll("\\s", "");
            if (!tel.matches("\\d{10}")) {
                exception.addFieldError("telephone", "Le téléphone doit contenir exactement 10 chiffres");
                hasError = true;
            }
        }
        
        if (hasError) {
            throw exception;
        }
    }
    
    public Integer compterApprentisPourNiveau(String niveau) {
        return apprentiRepository.compterApprentisPourNiveau(niveau);
    }
}

