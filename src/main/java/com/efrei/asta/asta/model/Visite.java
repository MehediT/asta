package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visite")
@Data
@NoArgsConstructor
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "date_visite")
    private LocalDate dateVisite;
    
    @Column(name = "format")
    private String format; // Visio ou Présentiel
    
    @Column(name = "commentaires", columnDefinition = "TEXT")
    private String commentaires;
}

