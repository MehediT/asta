package com.efrei.asta.asta.service;

import com.efrei.asta.asta.exception.InvalidDataException;
import com.efrei.asta.asta.model.Evaluation;
import com.efrei.asta.asta.repository.EvaluationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    
    private final EvaluationRepository evaluationRepository;
    
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }
    
    public List<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }
    
    public Optional<Evaluation> findById(Integer id) {
        return evaluationRepository.findById(id);
    }
    
    @Transactional
    public Evaluation save(Evaluation evaluation) {
        validerEvaluation(evaluation);
        return evaluationRepository.save(evaluation);
    }
    
    @Transactional
    public void delete(Integer id) {
        evaluationRepository.deleteById(id);
    }
    
    public void validerEvaluation(Evaluation evaluation) {
        if (evaluation == null) {
            return;
        }
        
        InvalidDataException exception = new InvalidDataException("Certains champs de l'évaluation sont invalides");
        boolean hasError = false;
        
        // Validation de la note du mémoire/rapport
        if (evaluation.getNoteMemoire() != null) {
            if (evaluation.getNoteMemoire() < 0 || evaluation.getNoteMemoire() > 20) {
                exception.addFieldError("evaluation.noteMemoire", "La note du mémoire doit être entre 0 et 20");
                hasError = true;
            }
        }
        
        // Validation de la note de soutenance
        if (evaluation.getNoteSoutenance() != null) {
            if (evaluation.getNoteSoutenance() < 0 || evaluation.getNoteSoutenance() > 20) {
                exception.addFieldError("evaluation.noteSoutenance", "La note de soutenance doit être entre 0 et 20");
                hasError = true;
            }
        }
        
        // Validation de la date de soutenance
        if (evaluation.getDateSoutenance() != null) {
            if (evaluation.getDateSoutenance().isAfter(LocalDate.now().plusYears(1))) {
                exception.addFieldError("evaluation.dateSoutenance", "La date de soutenance ne peut pas être dans plus d'un an");
                hasError = true;
            }
        }
        
        if (hasError) {
            throw exception;
        }
    }
}

