# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY gateway-service/pom.xml gateway-service/pom.xml
COPY request-service/pom.xml request-service/pom.xml
COPY chat-service/pom.xml chat-service/pom.xml
COPY gateway-service/src gateway-service/src
RUN mvn package -pl gateway-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /build/gateway-service/target/gateway-service-1.0.0.jar app.jar
EXPOSE 8080
ENV PORT=8080
CMD ["java", "-jar", "app.jar"]
