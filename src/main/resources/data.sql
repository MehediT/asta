-- Script SQL pour initialiser la base de données ASTA
-- Ce script crée un tuteur enseignant par défaut pour se connecter

-- Insertion d'un tuteur enseignant par défaut
-- Login: tuteur | Mot de passe: password123
INSERT INTO tuteur_enseignant (nom, prenom, login, mot_de_passe, email) 
VALUES ('Augustin', 'Jacques', 'tuteur', 'password123', 'jacques.augustin@efrei.fr');

-- Note: Les tables sont créées automatiquement par Hibernate (spring.jpa.hibernate.ddl-auto=create-drop)
-- Ce script insère uniquement les données initiales nécessaires

