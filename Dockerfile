FROM eclipse-temurin:17-jre-alpine

COPY target/Jwt-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT java -jar Jwt-service-0.0.1-SNAPSHOT.jar