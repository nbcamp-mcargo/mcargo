ARG SERVICE_NAME

FROM gradle:8.10.0-jdk17 AS builder
WORKDIR /workspace
COPY . .
RUN ./gradlew :${SERVICE_NAME}:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/${SERVICE_NAME}/build/libs/${SERVICE_NAME}-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]