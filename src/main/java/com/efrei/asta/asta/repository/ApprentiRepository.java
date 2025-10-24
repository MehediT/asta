package com.efrei.asta.asta.repository;

import com.efrei.asta.asta.model.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprentiRepository extends JpaRepository<Apprenti, Integer> {
    
    // Requêtes prédéfinies
    List<Apprenti> findByArchiveFalse();
    
    List<Apprenti> findByNiveau(String niveau);
    
    // Requête JPQL pour rechercher par nom (insensible à la casse)
    @Query("SELECT a FROM Apprenti a WHERE LOWER(a.nom) LIKE LOWER(CONCAT('%', :nom, '%')) AND a.archive = false")
    List<Apprenti> rechercherParNom(@Param("nom") String nom);
    
    // Requête JPQL pour rechercher par entreprise
    @Query("SELECT a FROM Apprenti a WHERE LOWER(a.entreprise.raisonSociale) LIKE LOWER(CONCAT('%', :entreprise, '%')) AND a.archive = false")
    List<Apprenti> rechercherParEntreprise(@Param("entreprise") String entreprise);
    
    // Requête JPQL pour rechercher par mot-clé de mission
    @Query("SELECT a FROM Apprenti a WHERE LOWER(a.mission.motsCles) LIKE LOWER(CONCAT('%', :motCle, '%')) AND a.archive = false")
    List<Apprenti> rechercherParMotCleMission(@Param("motCle") String motCle);
    
    // Requête JPQL pour rechercher par année académique
    @Query("SELECT a FROM Apprenti a WHERE a.anneeAcademique = :anneeAcademique")
    List<Apprenti> rechercherParAnneeAcademique(@Param("anneeAcademique") String anneeAcademique);
    
    // Requête SQL native pour récupérer les statistiques (exemple d'utilisation de SQL natif)
    @Query(value = "SELECT COUNT(*) FROM apprenti WHERE archive = false AND niveau = :niveau", nativeQuery = true)
    Integer compterApprentisPourNiveau(@Param("niveau") String niveau);
    
    // Requête JPQL pour récupérer les apprentis d'un tuteur pour l'année en cours
    @Query("SELECT a FROM Apprenti a WHERE a.tuteurEnseignant.id = :tuteurId AND a.anneeAcademique = :anneeAcademique AND a.archive = false")
    List<Apprenti> findByTuteurEnseignantIdAndAnneeAcademique(@Param("tuteurId") Integer tuteurId, @Param("anneeAcademique") String anneeAcademique);
}

