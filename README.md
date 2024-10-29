# 🎯 Kahoot Clone API

Une API REST reproduisant les fonctionnalités principales de Kahoot!, permettant la création et la gestion de quiz

## 📝 Table des matières

- [Aperçu](#-aperçu)

- [Fonctionnalités clés](#-fonctionnalités-clés)

- [Architecture technique](#-architecture-technique)

- [Installation](#️-installation)

- [Structure du projet](#-structure-du-projet)

- [API Endpoints](#-api-endpoints)

- [Documentation API](#-documentation-api)

- [Sécurité](#-sécurité)

- [Monitoring et Logging](#-monitoring-et-logging)

- [Modèle de données](#-modèle-de-données)

- [Contribution](#-contribution)

- [Licence](#-licence)

## 🚀 Aperçu

Ce projet est une implémentation backend d'un système similaire à [Kahoot](https://kahoot.com), offrant une API RESTpour la création et la gestion de quiz. L'application permet non seulement la création de quiz, des questions associées mais aussi la gestion de sessions de jeu avec suivi des scores et classements des joueurs.

## ✨ Fonctionnalités clés

### Gestion des utilisateurs

- Inscription et authentification via Keycloak (IAM)

- Gestion des roles (User/Admin)

- Gestion des profils utilisateurs

### Gestion des Kahoots (Quiz)

- CRUD complet des quiz

### Gestion des Questions

- Support de deux types de questions :

- Questions à choix multiples (QCM)

- Questions vrai/faux

- Système de points configurable

- Ordre personnalisable des questions

### Sessions de jeu

- Création de sessions de jeu

- Système de rejoindre une partie via code

- Classement dynamique des joueurs

- Historique des parties

## 🛠 Architecture technique

### Technologies principales

- Java 17

- Spring Boot 3.x

- Spring Data JPA

- Spring Security

- Keycloak (IAM)

- MySQL/MariaDB

- Maven

- MapStruct

- Lombok

- Spring AOP

- Swagger/OpenAPI

- JUnit 5

### Sécurité

- Authentification via Keycloak

- Gestion des rôles (User/Admin)

- Protection des routes sensibles

- JWT Token

## ⚙️ Installation

### Prérequis

```bash

- Java 17 ou supérieur

- Maven 3.8+

- MySQL/MariaDB

- Keycloak Server

```

### Configuration

1\. Cloner le repository

```bash

git clone https://github.com/kouroumapaul/kahoot.git

cd kahoot

```

2\. Configurer Keycloak

- Créer un realm

- Configurer les clients

- Définir les rôles (user, admin)


3\. Configurer l'application

```properties

# src/main/resources/application.properties

# Database

spring.datasource.url=jdbc:mysql://localhost:3306/kahoot_clone

spring.datasource.username=your_username

spring.datasource.password=your_password

# Keycloak

spring.security.oauth2.resourceserver.jwt.issuer-uri=http://votre_ip:8080/auth/realms/votre_realm

spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://votre_ip:8080/auth/realms/votre_realm/protocol/openid-connect/certs

```

4\. Compiler et lancer

```bash

mvn clean install

mvn spring-boot:run

```

## 📁 Structure du projet

```

src/

├── main/

│   ├── java/

│   │   └── com/example/kahoot/

│   │       ├── config/          # Configurations Spring et Keycloak

│   │       ├── controller/      # REST Controllers

│   │       ├── dto/            # Data Transfer Objects

│   │       ├── mapper/         # MapStruct Mappers

│   │       ├── model/          # Entités JPA

│   │       ├── repository/     # Repositories Spring Data

│   │       ├── service/        # Logique métier

│   │       ├── aspect/         # Aspects pour logging

│   │       └── security/       # Configuration sécurité

│   └── resources/

│       └── application.properties

└── test/

```

## 🔌 API Endpoints

### Kahoots

```

GET    /api/kahoots       # Liste des kahoots

POST   /api/kahoots       # Création d'un kahoot

GET    /api/kahoots/{id}  # Détails d'un kahoot

PUT    /api/kahoots/{id}  # Modification d'un kahoot

DELETE /api/kahoots/{id}  # Suppression d'un kahoot

```

### Questions

```

POST   /api/questions     # Ajout question

```

### Sessions de jeu

```

POST   /api/games                     # Création session

GET    /api/games/{gemePin}           # État session

GET    /api/sessions/{id}/results     # Classement

```

## 📚 Documentation API

La documentation complète de l'API est disponible via Swagger UI :

```

http://localhost:8080/api/docs

```

## 🔒 Sécurité

- Authentification via Keycloak

- Protection des routes sensibles via Spring Security

- Gestion des rôles :

- USER : Peut participer aux quiz

- ADMIN : Peut créer/modifier des quiz

## 📊 Monitoring et Logging

- Aspect Programming (AOP) pour le logging des :

- Appels aux contrôleurs

- Erreurs d'exécution

- Temps de réponse

## 📊 Modèle de données

### Entités principales

- **User** : Informations utilisateur

- **Kahoot** : Quiz et ses métadonnées

- **Question** : Questions (abstraite)

- **MultiChoiceQuestion** : Questions QCM

- **TrueFalseQuestion** : Questions V/F

- **GameSession** : Session de jeu 

- **Player** : Joueur dans une session


### Relations clés

- Un `User` peut créer plusieurs `Kahoot`

- Un `Kahoot` contient plusieurs `Question` (QCM ou V/F)

- Une `Question QCM` a plusieurs `Answers` possibles et une seule réponse correcte

- Une `GameSession` est liée à un `Kahoot`

- Un `Player` participe à une `GameSession` (pour chaque GameSession le player est unique)


## 👥 Auteurs

- **Paul Kourouma** -  [kouroumapaul](https://github.com/kouroumapaul)
- **Daouda Traoré** -  [Trapuce](https://github.com/Trapuce)


Développé avec ❤️ 