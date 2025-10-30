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
     * Crée une nouvelle année académique :
     * - Archive les I3
     * - Promeut I2 → I3
     * - Promeut I1 → I2
     */
    @Transactional
    public void creerNouvelleAnneeAcademique(String nouvelleAnnee) {
        List<Apprenti> tousLesApprentis = apprentiRepository.findByArchiveFalse();
        
        for (Apprenti apprenti : tousLesApprentis) {
            String niveauActuel = apprenti.getNiveau();
            
            if ("I3".equalsIgnoreCase(niveauActuel)) {
                apprenti.setArchive(true);
            } else if ("I2".equalsIgnoreCase(niveauActuel)) {
                apprenti.setNiveau("I3");
            } else if ("I1".equalsIgnoreCase(niveauActuel)) {
                apprenti.setNiveau("I2");
            }
            
            // Mettre à jour l'année académique pour TOUS les apprentis non archivés
            if (!apprenti.getArchive()) {
                apprenti.setAnneeAcademique(nouvelleAnnee);
            }
            
            apprentiRepository.save(apprenti);
        }
    }
    
    public Integer compterApprentisPourNiveau(String niveau) {
        return apprentiRepository.compterApprentisPourNiveau(niveau);
    }
    
    public List<Apprenti> getApprentisParAnnee(String anneeAcademique) {
        return apprentiRepository.rechercherParAnneeAcademique(anneeAcademique);
    }
}

