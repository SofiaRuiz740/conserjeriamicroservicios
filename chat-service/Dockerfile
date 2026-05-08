# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY gateway-service/pom.xml gateway-service/pom.xml
COPY request-service/pom.xml request-service/pom.xml
COPY chat-service/pom.xml chat-service/pom.xml
COPY chat-service/src chat-service/src
RUN mvn package -pl chat-service -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /build/chat-service/target/chat-service-1.0.0.jar app.jar
EXPOSE 8082
ENV PORT=8082
CMD ["java", "-jar", "app.jar"]
