package com.efrei.asta.asta.repository;

import com.efrei.asta.asta.model.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprentiRepository extends JpaRepository<Apprenti, Integer> {
    
    List<Apprenti> findByArchiveFalse();
    
    List<Apprenti> findByNiveau(String niveau);
    
    @Query("SELECT a FROM Apprenti a WHERE LOWER(a.nom) LIKE LOWER(CONCAT('%', :nom, '%')) AND a.archive = false")
    List<Apprenti> rechercherParNom(@Param("nom") String nom);
    
    @Query("SELECT a FROM Apprenti a WHERE LOWER(a.entreprise.raisonSociale) LIKE LOWER(CONCAT('%', :entreprise, '%')) AND a.archive = false")
    List<Apprenti> rechercherParEntreprise(@Param("entreprise") String entreprise);
    
    @Query("SELECT a FROM Apprenti a WHERE LOWER(a.mission.motsCles) LIKE LOWER(CONCAT('%', :motCle, '%')) AND a.archive = false")
    List<Apprenti> rechercherParMotCleMission(@Param("motCle") String motCle);
    
    @Query("SELECT a FROM Apprenti a WHERE a.anneeAcademique = :anneeAcademique")
    List<Apprenti> rechercherParAnneeAcademique(@Param("anneeAcademique") String anneeAcademique);
    
    /**
     * Requête SQL native pour compter les apprentis par niveau.
     * 
     * AVANTAGES :
     * - Compatibilité directe avec MariaDB/MySQL
     * - Requête d'agrégation simple et performante
     * 
     * COMPROMIS :
     * - Dépendance au dialecte SQL du SGBD (LOWER() fonctionne avec MySQL/MariaDB/PostgreSQL)
     * - Code moins maintenable que JPQL
     * - Nécessite des tests supplémentaires si le SGBD change
     * 
     * ALTERNATIVE (avec JPQL) :
     * SELECT COUNT(a) FROM Apprenti a WHERE a.archive = false AND a.niveau = :niveau
     */
    @Query(value = "SELECT COUNT(*) FROM apprenti WHERE archive = false AND niveau = :niveau", nativeQuery = true)
    Integer compterApprentisPourNiveau(@Param("niveau") String niveau);
    
    @Query("SELECT a FROM Apprenti a WHERE a.tuteurEnseignant.id = :tuteurId AND a.anneeAcademique = :anneeAcademique AND a.archive = false")
    List<Apprenti> findByTuteurEnseignantIdAndAnneeAcademique(@Param("tuteurId") Integer tuteurId, @Param("anneeAcademique") String anneeAcademique);
}

