package com.efrei.asta.asta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "format")
    @Enumerated(EnumType.STRING)
    private FormatVisite format;
    
    @Column(name = "commentaires", columnDefinition = "TEXT")
    private String commentaires;
    
    @ManyToOne
    @JoinColumn(name = "apprenti_id", referencedColumnName = "id")
    private Apprenti apprenti;

    public enum FormatVisite {
        VISIO,
        PRESENTIEL
    }

    @Override
    public String toString() {
        return "Visite{" +
                "id=" + id +
                ", date=" + date +
                ", format=" + format +
                ", commentaires='" + commentaires + '\'' +
                '}';
    }
}

