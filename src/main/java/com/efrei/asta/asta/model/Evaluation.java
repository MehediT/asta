package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // Mémoire / Rapport
    @Column(name = "memoire_theme_sujet", columnDefinition = "TEXT")
    private String memoireThemeSujet;
    
    @Column(name = "memoire_note_finale")
    private Double memoireNotefinale;
    
    @Column(name = "memoire_commentaires", columnDefinition = "TEXT")
    private String memoireCommentaires;
    
    // Soutenance
    @Column(name = "soutenance_date")
    private LocalDate soutenanceDate;
    
    @Column(name = "soutenance_note_finale")
    private Double soutenanceNoteFinale;
    
    @Column(name = "soutenance_commentaires", columnDefinition = "TEXT")
    private String soutenanceCommentaires;
    
    @OneToOne
    @JoinColumn(name = "apprenti_id", referencedColumnName = "id")
    private Apprenti apprenti;

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", memoireThemeSujet='" + memoireThemeSujet + '\'' +
                ", memoireNotefinale=" + memoireNotefinale +
                ", memoireCommentaires='" + memoireCommentaires + '\'' +
                ", soutenanceDate=" + soutenanceDate +
                ", soutenanceNoteFinale=" + soutenanceNoteFinale +
                ", soutenanceCommentaires='" + soutenanceCommentaires + '\'' +
                '}';
    }
}

