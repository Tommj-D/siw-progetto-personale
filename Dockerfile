# Fase 1: build con Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY siw-project/pom.xml .
COPY siw-project/src ./src
RUN mvn clean package -DskipTests

# Fase 2: immagine finale, solo runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/siw-project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]