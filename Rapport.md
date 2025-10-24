# Rapport du Projet ASTA

**Application de Suivi de Tutorats d'Apprentis**

---

## 1. Informations de connexion

- **Login** : `tuteur`
- **Mot de passe** : `password123`

---

## 2. Outillage utilisé

### 2.1 IDE

- **IntelliJ IDEA** / **VS Code** (selon la préférence)

### 2.2 SGBD

- **MariaDB 11.x** (via Docker)
- Base de données : `asta`

---

## 3. Instructions pour lancer l'application

### Prérequis

1. Java 21 installé
2. Maven installé
3. Docker installé (pour MariaDB)

### Étapes de lancement

1. **Démarrer la base de données MariaDB** :

   ```bash
   docker-compose up -d
   ```

2. **Compiler et lancer l'application** :

   ```bash
   ./mvnw spring-boot:run
   ```

   Ou sous Windows :

   ```bash
   mvnw.cmd spring-boot:run
   ```

3. **Accéder à l'application** :
   - URL : `http://localhost:8080`
   - Login : `tuteur`
   - Mot de passe : `password123`

### Arrêter l'application

```bash
docker-compose down
```

---

## 4. Fonctionnalités implémentées

### ✅ Authentification

- Écran de connexion avec login/mot de passe
- Gestion de session (affichage "Bonjour [Prénom] | Déconnectez-vous")
- Messages d'erreur en cas d'échec de connexion

### ✅ Gestion des apprentis

- **Affichage** : Tableau de bord synthétique avec liste des apprentis de l'année en cours
- **Détails** : Page dédiée affichant toutes les informations d'un apprenti
- **Ajout** : Formulaire complet pour créer un nouvel apprenti
- **Modification** : Modification de tous les champs avec validation
- **Suppression** : Suppression d'un apprenti avec confirmation

### ✅ Recherche

- Recherche par **nom**
- Recherche par **entreprise**
- Recherche par **mot-clé de la mission**
- Recherche par **année académique**

### ✅ Nouvelle année académique

- Archivage automatique des apprentis I3
- Promotion automatique des I2 vers I3
- Promotion automatique des I1 vers I2
- Mise à jour de l'année académique

### ✅ Messages d'erreur et de succès

- Messages de succès après création/modification/suppression
- Messages d'erreur clairs avec indication des champs problématiques

---

## 5. Aspects techniques

### 5.1 Architecture

- **Pattern MVC** : Modèle-Vue-Contrôleur
- **Séparation des couches** :
  - `model` : Entités JPA
  - `repository` : Repositories Spring Data JPA
  - `service` : Logique métier
  - `controller` : Contrôleurs Spring MVC
  - `exception` : Exceptions personnalisées et gestion globale

### 5.2 Gestion des exceptions

- **ControllerAdvice** pour la gestion globale des exceptions
- **Exceptions personnalisées** :
  - `ApprentiNotFoundException` : Apprenti introuvable
  - `AuthenticationException` : Erreur d'authentification
  - `InvalidDataException` : Données invalides avec détails des champs en erreur
- Messages d'erreur en français, clairs et utiles

### 5.3 Persistance

#### Requêtes prédéfinies (Query Methods)

- `findByArchiveFalse()` : Récupérer les apprentis non archivés
- `findByNiveau(String niveau)` : Récupérer par niveau
- `findByLogin(String login)` : Récupérer un tuteur par login

#### Requêtes JPQL

- `rechercherParNom` : Recherche insensible à la casse
- `rechercherParEntreprise` : Recherche dans les entreprises
- `rechercherParMotCleMission` : Recherche dans les mots-clés de mission
- `rechercherParAnneeAcademique` : Recherche par année
- `findByTuteurEnseignantIdAndAnneeAcademique` : Filtrage par tuteur et année

#### Requête SQL native

- `compterApprentisPourNiveau` : Compte les apprentis par niveau (exemple d'utilisation de SQL natif)

**Analyse critique du SQL natif** :  
L'utilisation de SQL natif peut poser des problèmes de portabilité entre différents SGBD et rend le code moins maintenable. Dans ce projet, nous l'avons utilisé uniquement pour démonstration. En production, il serait préférable d'utiliser JPQL ou les Query Methods qui sont indépendants du SGBD.

#### Utilisation de @Transactional

- Annotée sur les méthodes de service qui modifient des données (`save`, `update`, `delete`, `creerNouvelleAnneeAcademique`)
- Garantit la cohérence des données et le rollback en cas d'erreur
- Particulièrement importante pour `creerNouvelleAnneeAcademique` qui effectue plusieurs modifications atomiques

---

## 6. Réponses aux questions

### a) Sur quel aspect de votre travail souhaitez-vous attirer l'attention du correcteur ?

J'ai particulièrement soigné **l'interface utilisateur** en utilisant Tailwind CSS pour créer une application moderne et intuitive. Chaque page est cohérente avec les autres, avec :

- Des messages de succès/erreur clairs et visibles
- Une navigation fluide avec fil d'Ariane
- Des formulaires bien structurés avec validation
- Des icônes SVG pour améliorer la lisibilité

De plus, la **gestion des exceptions** est complète avec des messages en français adaptés à chaque situation.

### b) Quelle est la plus grande difficulté que vous avez rencontrée ? Comment avez-vous géré/solutionné/contourné cette difficulté ?

La plus grande difficulté a été la **gestion des relations entre entités** (Apprenti, Entreprise, Mission, etc.). Initialement, les objets imbriqués n'étaient pas correctement initialisés dans les formulaires, ce qui causait des `NullPointerException`.

**Solution** : J'ai ajouté une initialisation systématique des objets liés dans les contrôleurs (méthode `nouveauApprentiForm` et `modifierApprentiForm`) et utilisé `CascadeType.PERSIST` et `CascadeType.MERGE` pour assurer la sauvegarde en cascade.

### c) Quelle a été la contribution de chaque membre de l'équipe ?

Projet réalisé individuellement.

### d) Si vous deviez retenir trois points de ce cours en général et de ce projet en particulier, quels seraient ces trois points ?

1. **Architecture en couches (MVC)** : Séparation claire des responsabilités entre modèle, vue et contrôleur, facilitant la maintenance et l'évolution du code.

2. **Spring Data JPA** : Puissance et simplicité des repositories Spring Data pour l'accès aux données, avec les Query Methods et JPQL qui évitent d'écrire du SQL.

3. **Gestion des exceptions** : Importance d'une gestion centralisée et cohérente des erreurs pour offrir une bonne expérience utilisateur et faciliter le débogage.

### e) Les fonctionnalités que vous n'avez pas eu le temps de mettre en œuvre et pourquoi

Toutes les fonctionnalités demandées ont été implémentées. Les fonctionnalités bonus qui n'ont pas été implémentées :

- **Import de données via fichier** : Nécessiterait l'ajout d'Apache POI ou d'une bibliothèque similaire pour parser les fichiers Excel/CSV
- **Documentation Swagger** : Par manque de temps, mais pourrait être ajoutée facilement avec SpringDoc OpenAPI

### f) À quel niveau, dans votre projet, avez-vous réussi à respecter entièrement ou partiellement les principes SOLID ?

#### S - Single Responsibility Principle ✅

Chaque classe a une responsabilité unique :

- Les `Controller` gèrent uniquement les requêtes HTTP
- Les `Service` contiennent la logique métier
- Les `Repository` gèrent l'accès aux données
- Les entités (`@Entity`) représentent uniquement le modèle de données

#### O - Open/Closed Principle ⚠️

Partiellement respecté. Les services sont ouverts à l'extension grâce aux interfaces, mais certaines méthodes pourraient être plus extensibles.

#### L - Liskov Substitution Principle ✅

Les interfaces de repository peuvent être substituées sans problème (principe de Spring Data JPA).

#### I - Interface Segregation Principle ✅

Les interfaces sont spécifiques et ne forcent pas l'implémentation de méthodes non utilisées.

#### D - Dependency Inversion Principle ✅

Utilisation de l'injection de dépendances de Spring :

- Les controllers dépendent des interfaces de service (abstraction)
- Les services dépendent des interfaces de repository (abstraction)
- Aucune dépendance directe vers des implémentations concrètes

---

## 7. Structure du projet

```
src/
├── main/
│   ├── java/com/efrei/asta/asta/
│   │   ├── controller/          # Contrôleurs MVC
│   │   │   ├── AuthenticationController.java
│   │   │   └── ApprentiController.java
│   │   ├── exception/           # Exceptions personnalisées
│   │   │   ├── ApprentiNotFoundException.java
│   │   │   ├── AuthenticationException.java
│   │   │   ├── InvalidDataException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── model/               # Entités JPA
│   │   │   ├── Apprenti.java
│   │   │   ├── Entreprise.java
│   │   │   ├── MaitreApprentissage.java
│   │   │   ├── Mission.java
│   │   │   ├── Visite.java
│   │   │   ├── Evaluation.java
│   │   │   └── TuteurEnseignant.java
│   │   ├── repository/          # Repositories Spring Data
│   │   │   ├── ApprentiRepository.java
│   │   │   ├── EntrepriseRepository.java
│   │   │   ├── MaitreApprentissageRepository.java
│   │   │   ├── MissionRepository.java
│   │   │   ├── VisiteRepository.java
│   │   │   ├── EvaluationRepository.java
│   │   │   └── TuteurEnseignantRepository.java
│   │   ├── service/             # Services métier
│   │   │   ├── AuthenticationService.java
│   │   │   └── ApprentiService.java
│   │   └── AstaApplication.java # Classe principale
│   └── resources/
│       ├── application.properties # Configuration
│       ├── data.sql             # Données initiales
│       ├── static/
│       │   └── main.css         # Fichier CSS Tailwind
│       └── templates/           # Vues Thymeleaf
│           ├── login.html
│           ├── dashboard.html
│           ├── detailsApprenti.html
│           ├── nouveauApprenti.html
│           ├── modifierApprenti.html
│           ├── recherche.html
│           └── nouvelleAnnee.html
```

---

## 8. Technologies utilisées

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **Spring MVC**
- **Thymeleaf** (moteur de templates)
- **Tailwind CSS** (framework CSS)
- **MariaDB** (base de données)
- **Docker** (conteneurisation de la base de données)
- **Maven** (gestion des dépendances)
- **Lombok** (réduction du boilerplate)

---

## 9. Points d'amélioration possibles

1. **Sécurité** : Hachage des mots de passe avec BCrypt
2. **Validation** : Utilisation de Bean Validation (@Valid, @NotNull, etc.)
3. **Tests** : Tests unitaires et d'intégration
4. **Documentation API** : Swagger/OpenAPI
5. **Pagination** : Pour la liste des apprentis si le nombre devient important
6. **Filtres avancés** : Combinaison de plusieurs critères de recherche

---

## Conclusion

Le projet ASTA remplit tous les objectifs fixés dans le cahier des charges. L'application est fonctionnelle, complète et offre une interface utilisateur moderne et intuitive. Les principes de clean code et SOLID ont été respectés autant que possible, et la gestion des exceptions est robuste.
