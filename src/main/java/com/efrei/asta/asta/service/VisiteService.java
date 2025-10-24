package com.efrei.asta.asta.service;

import com.efrei.asta.asta.exception.InvalidDataException;
import com.efrei.asta.asta.model.Visite;
import com.efrei.asta.asta.repository.VisiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VisiteService {
    
    private final VisiteRepository visiteRepository;
    
    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }
    
    public List<Visite> findAll() {
        return visiteRepository.findAll();
    }
    
    public Optional<Visite> findById(Integer id) {
        return visiteRepository.findById(id);
    }
    
    @Transactional
    public Visite save(Visite visite) {
        validerVisite(visite);
        return visiteRepository.save(visite);
    }
    
    @Transactional
    public void delete(Integer id) {
        visiteRepository.deleteById(id);
    }
    
    public void validerVisite(Visite visite) {
        if (visite == null) {
            return;
        }
        
        InvalidDataException exception = new InvalidDataException("Certains champs de la visite sont invalides");
        boolean hasError = false;
        
        // Validation de la date si elle existe
        if (visite.getDateVisite() != null) {
            if (visite.getDateVisite().isAfter(LocalDate.now().plusYears(1))) {
                exception.addFieldError("visite.dateVisite", "La date de visite ne peut pas être dans plus d'un an");
                hasError = true;
            }
        }
        
        if (hasError) {
            throw exception;
        }
    }
}

