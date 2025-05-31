# E2E Dockerfile for running tests
FROM maven:3.9.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Optionally, pre-download dependencies for faster container startup:
RUN mvn dependency:go-offline 