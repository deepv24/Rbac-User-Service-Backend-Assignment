# ---------- Stage 1: Build jar ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ---------- Stage 2: Run jar ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the correct jar produced by Maven
COPY --from=builder /app/target/rbac-user-management-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

