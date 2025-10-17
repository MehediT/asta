package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "mission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "mots_cles", columnDefinition = "TEXT")
    private String motsCles;
    
    @Column(name = "metier_cible")
    private String metierCible;
    
    @Column(name = "commentaires", columnDefinition = "TEXT")
    private String commentaires;
    
    @OneToOne
    @JoinColumn(name = "apprenti_id", referencedColumnName = "id")
    private Apprenti apprenti;

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", motsCles='" + motsCles + '\'' +
                ", metierCible='" + metierCible + '\'' +
                ", commentaires='" + commentaires + '\'' +
                '}';
    }
}

