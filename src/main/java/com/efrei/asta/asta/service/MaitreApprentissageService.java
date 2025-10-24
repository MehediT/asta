package com.efrei.asta.asta.service;

import com.efrei.asta.asta.exception.InvalidDataException;
import com.efrei.asta.asta.model.MaitreApprentissage;
import com.efrei.asta.asta.repository.MaitreApprentissageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MaitreApprentissageService {
    
    private final MaitreApprentissageRepository maitreApprentissageRepository;
    
    public MaitreApprentissageService(MaitreApprentissageRepository maitreApprentissageRepository) {
        this.maitreApprentissageRepository = maitreApprentissageRepository;
    }
    
    public List<MaitreApprentissage> findAll() {
        return maitreApprentissageRepository.findAll();
    }
    
    public Optional<MaitreApprentissage> findById(Integer id) {
        return maitreApprentissageRepository.findById(id);
    }
    
    @Transactional
    public MaitreApprentissage save(MaitreApprentissage maitreApprentissage) {
        validerMaitreApprentissage(maitreApprentissage);
        return maitreApprentissageRepository.save(maitreApprentissage);
    }
    
    @Transactional
    public void delete(Integer id) {
        maitreApprentissageRepository.deleteById(id);
    }
    
    public void validerMaitreApprentissage(MaitreApprentissage maitreApprentissage) {
        if (maitreApprentissage == null) {
            return;
        }
        
        InvalidDataException exception = new InvalidDataException("Certains champs du maître d'apprentissage sont invalides");
        boolean hasError = false;
        
        if (maitreApprentissage.getTelephone() != null && !maitreApprentissage.getTelephone().isEmpty()) {
            String tel = maitreApprentissage.getTelephone().replaceAll("\\s", "");
            if (!tel.matches("\\d{10}")) {
                exception.addFieldError("maitreApprentissage.telephone", "Le téléphone du maître d'apprentissage doit contenir exactement 10 chiffres");
                hasError = true;
            }
        }
        
        if (maitreApprentissage.getEmail() != null && !maitreApprentissage.getEmail().isEmpty()) {
            if (!maitreApprentissage.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                exception.addFieldError("maitreApprentissage.email", "L'adresse email du maître d'apprentissage n'est pas valide");
                hasError = true;
            }
        }
        
        if (hasError) {
            throw exception;
        }
    }
}

