# Rapport - Projet ASTA

**Auteurs** : Mehedi TOURÉ, Adil CHETOUANI

**Application de Suivi de Tutorats d'Apprentis**

---

## 1. Connexion et Lancement

- **Login** : `tuteur`
- **Mot de passe** : `password123`

Pour lancer l'application :

```bash
docker-compose up -d
./mvnw spring-boot:run
```

Dans un autre terminal, pour le CSS en live :

```bash
cd src/frontend && npm run watch:css
```

Accédez à : `http://localhost:8080`

---

## 2. Fonctionnalités Implémentées

✅ Authentification avec gestion de session  
✅ Gestion complète des apprentis (CRUD)  
✅ Recherche multi-critères (nom, entreprise, mission, année)  
✅ Création de nouvelle année académique (promotions automatiques)  
✅ Gestion des entreprises, maîtres d'apprentissage, missions, visites et évaluations  
✅ Interface moderne avec Tailwind CSS  
✅ Messages d'erreur/succès explicites

---

## 3. Architecture

### MVC Pattern

- **Controllers** : Gestion des requêtes HTTP
- **Services** : Logique métier
- **Repositories** : Accès aux données
- **Models** : Entités JPA
- **Exceptions** : Gestion centralisée des erreurs

### Technologies utilisées

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- Thymeleaf
- Tailwind CSS
- MariaDB
- Docker

---

## 4. Points Techniques Importants

### Requête SQL Native

Conformément aux exigences, une seule requête SQL native a été utilisée :

```sql
SELECT COUNT(*) FROM apprenti WHERE archive = false AND niveau = :niveau
```

**Localisation** : `ApprentiRepository.java` ligne 52

**Pourquoi SQL natif ?**

- C'est une simple agrégation COUNT pour le dashboard
- Performante pour ce cas d'usage
- Paramètres sécurisés avec `@Param`

**Alternative JPQL qui aurait marché** :

```java
SELECT COUNT(a) FROM Apprenti a WHERE a.archive = false AND a.niveau = :niveau
```

Le reste du code utilise JPQL et Query Methods pour rester portable.

Pour plus de détails, voir [ANALYSE_SQL.md](./ANALYSE_SQL.md)

### Gestion des Relations

Les entités ont des relations complexes (Apprenti → Entreprise, Mission, etc.). Les objets liés sont initialisés systématiquement dans les contrôleurs pour éviter les NullPointerException.

Utilisation de `CascadeType.PERSIST` et `CascadeType.MERGE` pour les sauvegardes en cascade.

### @Transactional

Utilisé sur les méthodes de service qui modifient des données pour garantir l'atomicité et le rollback en cas d'erreur.

---

## 5. Réseau des Entités

```
Apprenti
├── Entreprise
├── Mission (avec mots-clés)
├── MaitreApprentissage
├── TuteurEnseignant
├── Visite (historique)
└── Evaluation
```

---

## 6. Réponses aux Questions du Cahier

### a) Quel aspect du travail vaut-il la peine d'attirer l'attention ?

L'interface utilisateur. On a utilisé Tailwind CSS pour créer quelque chose de moderne et cohérent. Les formulaires sont bien structurés, les messages d'erreur sont clairs, et la navigation est intuitive.

### b) Plus grande difficulté rencontrée ?

Les relations entre entités. Au début, les objets imbriqués n'étaient pas initialisés correctement dans les formulaires, ce qui causait des NullPointerException.

**Solution** : Initialisation systématique des objets liés dans les contrôleurs + utilisation de CascadeType pour la sauvegarde.

### c) Contribution de chaque membre ?

Projet réalisé en équipe (Mehedi TOURÉ et Adil CHETOUANI).

### d) Trois points clés du cours/projet ?

1. **MVC et séparation des couches** : Architecture claire qui facilite la maintenance
2. **Spring Data JPA** : Puissant mais simple pour l'accès aux données
3. **Gestion des exceptions** : Centralisée et utile pour l'expérience utilisateur

### e) Fonctionnalités non implémentées ?

Toutes les fonctionnalités requises ont été faites. Les bonus skippés :

- Import de fichiers Excel (nécessiterait Apache POI)
- Documentation Swagger (non critique)

### f) Principes SOLID appliqués ?

**S (Single Responsibility)** ✅  
Chaque classe a une seule responsabilité (Controllers gèrent les requêtes, Services la logique, etc.)

**O (Open/Closed)** ⚠️  
Partiellement. Les services pourraient être plus extensibles.

**L (Liskov Substitution)** ✅  
Les interfaces de repository peuvent être substituées sans problème.

**I (Interface Segregation)** ✅  
Les interfaces sont spécifiques et ne forcent pas l'implémentation de méthodes inutiles.

**D (Dependency Inversion)** ✅  
Injection de dépendances de Spring utilisée partout.

---

## 7. Améliorations Possibles

- Hachage des mots de passe avec BCrypt
- Bean Validation (@Valid, @NotNull)
- Tests unitaires/intégration
- Pagination pour les listes
- Cache (Redis) pour les requêtes fréquentes

---

## 8. Conclusion

Le projet remplit tous les objectifs. L'application est fonctionnelle, l'interface est propre, et le code est organisé. Rien de révolutionnaire, mais du travail solide et maintenable.
