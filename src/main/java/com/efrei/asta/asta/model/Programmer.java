package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "programmer")
@Data
@NoArgsConstructor
public class Programmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "adresse")
    private String adresse;
    
    @Column(name = "langage_expertise")
    private String langageExpertise;
    
    @Column(name = "livre_prefere")
    private String livrePrefere;
    
    @Column(name = "salaire")
    private Double salaire;

    public Programmer(Integer id, String nom, String prenom, String adresse, 
                     String langageExpertise, String livrePrefere, Double salaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.langageExpertise = langageExpertise;
        this.livrePrefere = livrePrefere;
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", langageExpertise='" + langageExpertise + '\'' +
                ", livrePrefere='" + livrePrefere + '\'' +
                ", salaire=" + salaire +
                '}';
    }
}
