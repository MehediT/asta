package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "apprenti")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apprenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "programme")
    private String programme;
    
    @Column(name = "annee_academique")
    private String anneeAcademique;
    
    @Column(name = "majeure")
    private String majeure;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "adresse_electronique")
    private String adresseElectronique;
    
    @Column(name = "telephone", length = 10)
    private String telephone;
    
    @OneToOne(mappedBy = "apprenti", cascade = CascadeType.ALL)
    private Entreprise entreprise;
    
    @OneToOne(mappedBy = "apprenti", cascade = CascadeType.ALL)
    private MaitreApprentissage maitreApprentissage;
    
    @OneToOne(mappedBy = "apprenti", cascade = CascadeType.ALL)
    private Mission mission;
    
    @OneToMany(mappedBy = "apprenti", cascade = CascadeType.ALL)
    private List<Visite> visites;
    
    @OneToOne(mappedBy = "apprenti", cascade = CascadeType.ALL)
    private Evaluation evaluation;
    
    @Column(name = "remarques", columnDefinition = "TEXT")
    private String remarques;
    
    @Column(name = "feedback_tuteur", columnDefinition = "TEXT")
    private String feedbackTuteur;

    @Override
    public String toString() {
        return "Apprenti{" +
                "id=" + id +
                ", programme='" + programme + '\'' +
                ", anneeAcademique='" + anneeAcademique + '\'' +
                ", majeure='" + majeure + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresseElectronique='" + adresseElectronique + '\'' +
                ", telephone='" + telephone + '\'' +
                ", remarques='" + remarques + '\'' +
                ", feedbackTuteur='" + feedbackTuteur + '\'' +
                '}';
    }
}

