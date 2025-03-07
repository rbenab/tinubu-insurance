# Tinubu Insurance

## Description

Tinubu Insurance est une application Spring Boot pour la gestion des assurances. Elle utilise PostgreSQL comme base de données et est configurée pour être exécutée dans des conteneurs Docker.

## Prérequis

Avant de pouvoir exécuter cette application, assurez-vous d'avoir installé Docker et Docker Compose sur votre machine.
L'application utilise openjdk:11-jre-slim comme image de base pour exécuter l'application Java dans le conteneur Docker.

## Lancer l'application

1. Clonez ce projet :
   ```bash
   git clone https://github.com/rbenab/tinubu-insurance.git
   cd tinubu-insurance
   
2. Construisez et lancez l'application avec Docker Compose : 
   ```bash
   docker-compose up --build

3. L'application sera disponible à l'adresse suivante : 

   http://localhost:8081/tinubu-insurance/server/status

   Réponse attendue : Server Active
  
5. Accédez aux API :
   Les endpoints de l'application sont disponibles sous :
   
   http://localhost:8081/tinubu-insurance/policies


