# 🚚 MCargo (MSA 기반 물류 관리 및 배송 플랫폼)

<aside>
💡 <b>개발 기간</b> : 2025.10.31 - 2025.11.13 <br>  
<b>개발 인원</b> : 이지은, 황교석, 이준성, 이시우, 허시영 (총 5명) <br> 
<b>프로젝트 개요</b> : MSA 기반 물류 관리 및 배송 플랫폼 개발
</aside>

---

## 🛠 개발 환경 소개

| 분류 | 상세 | 선택 이유 |
| :--- | :--- | :--- |
| **IDE** | IntelliJ | Spring Boot 개발 생산성과 코드 품질 최적화 |
| **Language** | Java 17 | LTS 기반의 안정성과 최신 문법 활용 |
| **Framework** | Spring Boot 3.4.11 | 빠른 개발과 확장성이 필요한 서비스에 최적화 |
| **Database** | PostgreSQL | 안정성과 고급 기능을 갖춘 오픈소스 RDBMS |
| **Build Tool** | Gradle | 유연한 빌드 환경을 제공하여 효율적인 CI/CD 구현 |
| **DevOps** | EC2, ECR, ALB, ACM, RDS, Docker | 개발·배포 환경의 일관성 유지 및 자동화 |

---

## ⚙️ 실행 방법

### 1️⃣ 환경 변수 설정 (`.env`)

프로젝트 루트에 `.env` 파일을 생성하고 아래 내용을 설정합니다.

```env
# ===============================
# 각 모듈 별 환경 설정
# ===============================

# DB (PostgreSQL) 설정
DB_HOST=
DB_PORT=
DB_NAME=

DB_URL=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
DB_USERNAME=
DB_PASSWORD=

# 서버 포트
PORT=

# Eureka
EUREKA_SERVICE_URL=

# 외부 API Key (Kakao, Slack, Gemini 등)
KAKAO_API_KEY=
GEMINI_API_KEY=
SLACK_TOKEN=
```

---

### 2️⃣ application.yml 설정
ex. slack-service <br>
`src/main/resources/application.yml`

```yaml
spring:
  application:
    name: slack-service

  config:
    import: optional:file:.env-slack[.properties]

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: ${PORT}

eureka:
  client:
    service-url:
      defaultZone: ${EUREKA_SERVICE_URL}

slack:
  token: ${SLACK_TOKEN}

gemini:
  api-key: ${GEMINI_API_KEY}
```

---

### 3️⃣ Docker Compose 환경 구성

PostgreSQL 실행을 위한 `docker-compose.yml` 예시입니다.

```yaml
services:
  postgres:
    image: postgres:latest
    container_name: sample-postgres
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: database_name
      POSTGRES_USER: your_username
      POSTGRES_PASSWORD: your_password
```

Docker 컨테이너 실행:

```bash
docker compose up -d
```

---

### 4️⃣ 애플리케이션 실행

Gradle을 통해 Spring Boot 애플리케이션을 실행합니다.

```bash
./gradlew bootRun
```

---

# 📊 설계 산출물

## 1️⃣ Domain Diagram

도메인 간 관계 및 책임을 정의한 다이어그램입니다.
<img width="2157" height="1452" alt="도메인 다이어그램_Domain Diagram" src="https://github.com/user-attachments/assets/4835ace1-e9fc-404c-9e2c-62cdd112318c" />


---

## 2️⃣ Sequence Diagram

주요 배송 및 주문흐름을 나타낸 시퀀스 다이어그램입니다.
<img width="3197" height="5872" alt="배송 시퀀스 변경(2)" src="https://github.com/user-attachments/assets/c94cab29-3031-4709-bb3f-c0e0f0534a1d" />


---

## 3️⃣ ERD

데이터베이스 테이블 구조 및 관계를 정의한 ERD입니다.
<img width="1309" height="760" alt="ERD 최종" src="https://github.com/user-attachments/assets/f683ff8b-b3ac-44ef-8633-55c7ef0d3454" />


---

##  4️⃣ Infrastructure Architecture

Eureka를 활용한 Service Discovery와 Docker 컨테이너 기반의 배포 구조입니다.
<img width="3786" height="3012" alt="인프라 설계서" src="https://github.com/user-attachments/assets/dea369f5-8b10-43f4-afbe-2c6f549be0fa" />

---
