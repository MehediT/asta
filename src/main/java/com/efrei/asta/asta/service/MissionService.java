package com.efrei.asta.asta.service;

import com.efrei.asta.asta.exception.InvalidDataException;
import com.efrei.asta.asta.model.Mission;
import com.efrei.asta.asta.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MissionService {
    
    private final MissionRepository missionRepository;
    
    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }
    
    public List<Mission> findAll() {
        return missionRepository.findAll();
    }
    
    public Optional<Mission> findById(Integer id) {
        return missionRepository.findById(id);
    }
    
    @Transactional
    public Mission save(Mission mission) {
        validerMission(mission);
        return missionRepository.save(mission);
    }
    
    @Transactional
    public void delete(Integer id) {
        missionRepository.deleteById(id);
    }
    
    public void validerMission(Mission mission) {
        if (mission == null) {
            return;
        }
        
        InvalidDataException exception = new InvalidDataException("Certains champs de la mission sont invalides");
        boolean hasError = false;
        
        // Validation des mots-clés si nécessaire
        if (mission.getMotsCles() != null && !mission.getMotsCles().trim().isEmpty()) {
            if (mission.getMotsCles().trim().length() < 3) {
                exception.addFieldError("mission.motsCles", "Les mots-clés doivent contenir au moins 3 caractères");
                hasError = true;
            }
        }
        
        if (hasError) {
            throw exception;
        }
    }
}

