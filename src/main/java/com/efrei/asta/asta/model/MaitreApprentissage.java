package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "maitre_apprentissage")
@Data
@NoArgsConstructor
public class MaitreApprentissage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @Column(name = "prenom", nullable = false)
    private String prenom;
    
    @Column(name = "poste")
    private String poste;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "remarques", columnDefinition = "TEXT")
    private String remarques;
}

