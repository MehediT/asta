package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "maitre_apprentissage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaitreApprentissage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "poste")
    private String poste;
    
    @Column(name = "adresse_electronique")
    private String adresseElectronique;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "remarques", columnDefinition = "TEXT")
    private String remarques;
    
    @OneToOne
    @JoinColumn(name = "apprenti_id", referencedColumnName = "id")
    private Apprenti apprenti;

    @Override
    public String toString() {
        return "MaitreApprentissage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", poste='" + poste + '\'' +
                ", adresseElectronique='" + adresseElectronique + '\'' +
                ", telephone='" + telephone + '\'' +
                ", remarques='" + remarques + '\'' +
                '}';
    }
}

