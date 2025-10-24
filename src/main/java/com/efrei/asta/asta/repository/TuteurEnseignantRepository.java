package com.efrei.asta.asta.repository;

import com.efrei.asta.asta.model.TuteurEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TuteurEnseignantRepository extends JpaRepository<TuteurEnseignant, Integer> {
    // Requête prédéfinie
    Optional<TuteurEnseignant> findByLogin(String login);
}

