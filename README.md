# ASTA - Application de Suivi de Tutorats d'Apprentis

Application web développée avec Spring Boot pour gérer le suivi des apprentis, leurs entreprises, missions, visites et évaluations.

## 🚀 Démarrage rapide

### Prérequis

- Java 21
- Maven
- Docker (pour MariaDB)

### Installation et lancement

1. **Démarrer la base de données** :

   ```bash
   docker-compose up -d
   ```

2. **Lancer l'application** :

   ```bash
   ./mvnw spring-boot:run
   ```

   Ou sous Windows :

   ```bash
   mvnw.cmd spring-boot:run
   ```

3. **Accéder à l'application** :
   - Ouvrir le navigateur : http://localhost:8080
   - **Login** : `tuteur`
   - **Mot de passe** : `password123`

### Arrêt

```bash
docker-compose down
```

## 📋 Fonctionnalités

- ✅ Authentification avec gestion de session
- ✅ Gestion complète des apprentis (CRUD)
- ✅ Gestion des entreprises et maîtres d'apprentissage
- ✅ Suivi des missions, visites et évaluations
- ✅ Recherche multi-critères (nom, entreprise, mot-clé, année)
- ✅ Création de nouvelle année académique (promotion automatique)
- ✅ Interface moderne avec Tailwind CSS
- ✅ Messages d'erreur et de succès explicites

## 📚 Documentation

Consultez le fichier [Rapport.md](Rapport.md) pour plus de détails sur :

- L'architecture du projet
- Les choix techniques
- Les principes SOLID appliqués
- La structure de la base de données

## 🛠️ Technologies

- Spring Boot 3.5.6
- Spring Data JPA
- Thymeleaf
- Tailwind CSS
- MariaDB
- Docker
- Lombok
- Maven

## 📁 Structure du projet

```
src/
├── main/
│   ├── java/.../asta/
│   │   ├── controller/      # Contrôleurs MVC
│   │   ├── service/         # Logique métier
│   │   ├── repository/      # Accès aux données
│   │   ├── model/           # Entités JPA
│   │   └── exception/       # Gestion des erreurs
│   └── resources/
│       ├── templates/       # Vues Thymeleaf
│       ├── static/          # CSS
│       └── application.properties
```

## 👤 Auteur

Projet développé dans le cadre du cours "Développement Full Stack avec Java" - EFREI Paris.
