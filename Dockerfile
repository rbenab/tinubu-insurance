# Étape 1: Utiliser une image Java comme base
FROM openjdk:11-jre-slim

# Étape 2: Ajouter l'artefact de l'application (JAR) dans l'image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Étape 3: Exposer le port sur lequel l'application va tourner
EXPOSE 8080

# Étape 4: Exécuter le JAR avec la commande Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]
