# Fase 1: build del frontend React
FROM node:20 AS frontend-build
WORKDIR /frontend
COPY siw-frontend/package*.json ./
RUN npm install
COPY siw-frontend/ ./
RUN npm run build

# Fase 2: build con Maven, includendo la build React appena generata
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY siw-project/pom.xml .
COPY siw-project/src ./src
COPY --from=frontend-build /frontend/build ./src/main/resources/static
RUN mvn clean package -DskipTests

# Fase 3: immagine finale, solo runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/siw-project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]