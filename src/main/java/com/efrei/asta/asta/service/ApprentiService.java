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
    private final EntrepriseService entrepriseService;
    private final MaitreApprentissageService maitreApprentissageService;
    private final MissionService missionService;
    private final VisiteService visiteService;
    private final EvaluationService evaluationService;
    
    public ApprentiService(ApprentiRepository apprentiRepository,
                          EntrepriseService entrepriseService,
                          MaitreApprentissageService maitreApprentissageService,
                          MissionService missionService,
                          VisiteService visiteService,
                          EvaluationService evaluationService) {
        this.apprentiRepository = apprentiRepository;
        this.entrepriseService = entrepriseService;
        this.maitreApprentissageService = maitreApprentissageService;
        this.missionService = missionService;
        this.visiteService = visiteService;
        this.evaluationService = evaluationService;
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
        
        // Valider les entités liées
        entrepriseService.validerEntreprise(apprenti.getEntreprise());
        maitreApprentissageService.validerMaitreApprentissage(apprenti.getMaitreApprentissage());
        missionService.validerMission(apprenti.getMission());
        visiteService.validerVisite(apprenti.getVisite());
        evaluationService.validerEvaluation(apprenti.getEvaluation());
        
        return apprentiRepository.save(apprenti);
    }
    
    @Transactional
    public Apprenti update(Integer id, Apprenti apprentiModifie) {
        Apprenti apprentiExistant = findByIdOrThrow(id);
        
        validerApprenti(apprentiModifie);
        
        // Valider les entités liées
        entrepriseService.validerEntreprise(apprentiModifie.getEntreprise());
        maitreApprentissageService.validerMaitreApprentissage(apprentiModifie.getMaitreApprentissage());
        missionService.validerMission(apprentiModifie.getMission());
        visiteService.validerVisite(apprentiModifie.getVisite());
        evaluationService.validerEvaluation(apprentiModifie.getEvaluation());
        
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
}

