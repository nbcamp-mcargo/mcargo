ARG SERVICE_NAME

FROM gradle:8.10.0-jdk17 AS builder
ARG SERVICE_NAME

WORKDIR /workspace

# 루트 gradle 파일들만 복사
COPY settings.gradle .
COPY build.gradle .

# 해당 서비스만 복사
COPY ${SERVICE_NAME}/ ${SERVICE_NAME}/

RUN ./gradlew :${SERVICE_NAME}:clean :${SERVICE_NAME}:build --no-daemon

FROM eclipse-temurin:17-jre
ARG SERVICE_NAME
WORKDIR /app

COPY --from=builder /workspace/${SERVICE_NAME}/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]