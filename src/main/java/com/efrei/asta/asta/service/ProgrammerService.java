package com.efrei.asta.asta.service;

import com.efrei.asta.asta.model.Programmer;
import com.efrei.asta.asta.repository.ProgrammerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgrammerService {
    
    private final ProgrammerRepository programmerRepository;
    
    public ProgrammerService(ProgrammerRepository programmerRepository) {
        this.programmerRepository = programmerRepository;
    }
    
    // 4.1.5 - Méthode pour récupérer tous les programmeurs
    public List<Programmer> findAll() {
        return programmerRepository.findAll();
    }
    
    // 4.2.1 - Méthode pour récupérer un programmeur par ID
    public Optional<Programmer> findOne(Integer id) {
        return programmerRepository.findById(id);
    }
    
    // 4.2.2 - Méthode avec gestion d'erreur pour affichage console
    public Programmer findOneWithConsoleLog(Integer id) {
        Optional<Programmer> programmer = programmerRepository.findById(id);
        if (programmer.isEmpty()) {
            System.out.println("Ce programmeur n'existe pas");
            return null;
        }
        return programmer.get();
    }
    
    // 4.3.1 & 4.3.2 - Méthode pour supprimer un programmeur
    public boolean delete(Integer id) {
        if (programmerRepository.existsById(id)) {
            programmerRepository.deleteById(id);
            return true;
        } else {
            System.out.println("Impossible de supprimer : ce programmeur n'existe pas");
            return false;
        }
    }
    
    // 4.4 - Méthode pour modifier partiellement un programmeur (PATCH)
    public Programmer patch(Integer id, Programmer partialUpdate) {
        Optional<Programmer> existingProgrammer = programmerRepository.findById(id);
        if (existingProgrammer.isPresent()) {
            BeanUtils.copyProperties(partialUpdate, existingProgrammer.orElseThrow(), "id");
            return programmerRepository.save(existingProgrammer.orElseThrow());
        } else {
            System.out.println("Impossible de modifier : ce programmeur n'existe pas");
            return null;
        }
    }
    
    // 4.5 - Méthode pour ajouter un nouveau programmeur
    public Programmer save(Programmer newProgrammer) {
        // L'ID sera généré automatiquement par la base de données
        return programmerRepository.save(newProgrammer);
    }
}
