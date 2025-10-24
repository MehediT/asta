package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "apprenti")
@Data
@NoArgsConstructor
public class Apprenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "programme", nullable = false)
    private String programme;
    
    @Column(name = "annee_academique", nullable = false)
    private String anneeAcademique;
    
    @Column(name = "majeure")
    private String majeure;
    
    @Column(name = "niveau") // I1, I2 ou I3
    private String niveau;
    
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @Column(name = "prenom", nullable = false)
    private String prenom;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "archive")
    private Boolean archive = false;
    
    @Column(name = "feedback_tuteur", columnDefinition = "TEXT")
    private String feedbackTuteur;
    
    // Relations
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "maitre_apprentissage_id")
    private MaitreApprentissage maitreApprentissage;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "mission_id")
    private Mission mission;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "visite_id")
    private Visite visite;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;
    
    @ManyToOne
    @JoinColumn(name = "tuteur_enseignant_id")
    private TuteurEnseignant tuteurEnseignant;
}

