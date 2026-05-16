# =========================
# Stage 1: Build application
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy source code
COPY . .

# Build jar
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Run application
# =========================
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Start Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]