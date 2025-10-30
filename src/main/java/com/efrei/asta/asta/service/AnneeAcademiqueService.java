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
    
    public Integer compterApprentisPourNiveau(String niveau) {
        return apprentiRepository.compterApprentisPourNiveau(niveau);
    }
    
    public List<Apprenti> getApprentisParAnnee(String anneeAcademique) {
        return apprentiRepository.rechercherParAnneeAcademique(anneeAcademique);
    }
}

