ARG SERVICE_NAME

FROM gradle:8.10.0-jdk17 AS builder
ARG SERVICE_NAME

WORKDIR /workspace

# Gradle Wrapper 복사 (필수)
COPY gradlew .
COPY gradle/ gradle/
RUN chmod +x gradlew

# 루트 gradle 파일 복사
COPY settings.gradle .
COPY build.gradle .

# 서비스 소스 복사
COPY ${SERVICE_NAME}/ ${SERVICE_NAME}/

# 빌드 실행
RUN ./gradlew :${SERVICE_NAME}:clean :${SERVICE_NAME}:build --no-daemon

FROM eclipse-temurin:17-jre
ARG SERVICE_NAME

WORKDIR /app

COPY --from=builder /workspace/${SERVICE_NAME}/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]