package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evaluation")
@Data
@NoArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // Mémoire / Rapport
    @Column(name = "theme_memoire")
    private String themeMemoire;
    
    @Column(name = "note_memoire")
    private Double noteMemoire;
    
    @Column(name = "commentaires_memoire", columnDefinition = "TEXT")
    private String commentairesMemoire;
    
    // Soutenance
    @Column(name = "date_soutenance")
    private LocalDate dateSoutenance;
    
    @Column(name = "note_soutenance")
    private Double noteSoutenance;
    
    @Column(name = "commentaires_soutenance", columnDefinition = "TEXT")
    private String commentairesSoutenance;
    
    @Column(name = "remarques", columnDefinition = "TEXT")
    private String remarques;
}

