# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/target/Jwt-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT java -jar Jwt-service-0.0.1-SNAPSHOT.jar