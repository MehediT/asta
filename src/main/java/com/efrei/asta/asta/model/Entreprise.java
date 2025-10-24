package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "entreprise")
@Data
@NoArgsConstructor
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "raison_sociale", nullable = false)
    private String raisonSociale;
    
    @Column(name = "adresse")
    private String adresse;
    
    @Column(name = "infos_acces", columnDefinition = "TEXT")
    private String infosAcces;
}

