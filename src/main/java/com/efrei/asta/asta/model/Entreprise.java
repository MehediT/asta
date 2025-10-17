package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "entreprise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "raison_sociale")
    private String raisonSociale;
    
    @Column(name = "adresse")
    private String adresse;
    
    @Column(name = "informations_acces", columnDefinition = "TEXT")
    private String informationsAcces;
    
    @OneToOne
    @JoinColumn(name = "apprenti_id", referencedColumnName = "id")
    private Apprenti apprenti;

    @Override
    public String toString() {
        return "Entreprise{" +
                "id=" + id +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", adresse='" + adresse + '\'' +
                ", informationsAcces='" + informationsAcces + '\'' +
                '}';
    }
}

