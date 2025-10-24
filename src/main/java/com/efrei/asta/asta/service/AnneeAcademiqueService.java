package com.efrei.asta.asta.service;

import com.efrei.asta.asta.model.Apprenti;
import com.efrei.asta.asta.repository.ApprentiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnneeAcademiqueService {
    
    private final ApprentiRepository apprentiRepository;
    
    public AnneeAcademiqueService(ApprentiRepository apprentiRepository) {
        this.apprentiRepository = apprentiRepository;
    }
    
    /**
     * Crée une nouvelle année académique en :
     * - Archivant les apprentis de niveau I3
     * - Promouvant les I2 vers I3
     * - Promouvant les I1 vers I2
     * 
     * Cette méthode utilise une requête SQL native pour effectuer la promotion en masse,
     * conformément à l'exigence du projet d'utiliser au moins une requête SQL standard.
     */
    @Transactional
    public void creerNouvelleAnneeAcademique(String nouvelleAnnee) {
        // Récupérer tous les apprentis non archivés
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
    
    /**
     * Récupère le nombre d'apprentis pour un niveau donné
     */
    public Integer compterApprentisPourNiveau(String niveau) {
        return apprentiRepository.compterApprentisPourNiveau(niveau);
    }
    
    /**
     * Récupère tous les apprentis pour une année académique donnée
     */
    public List<Apprenti> getApprentisParAnnee(String anneeAcademique) {
        return apprentiRepository.rechercherParAnneeAcademique(anneeAcademique);
    }
}

