# MSA Architecture Skeleton

본 프로젝트는 Spring Boot, Spring Cloud, Kotlin을 기반으로 하는 마이크로서비스 아키텍처(MSA)의 기본 골격을 제공하기 위해 설계되었습니다. 실제 프로덕션 환경에서 마주할 수 있는 다양한 기술적 과제들을 해결하기 위한 모범 사례와 패턴을 적용하였습니다.

## 주요 기술 스택

- **언어**: Kotlin
- **프레임워크**: Spring Boot, Spring Cloud
- **API 게이트웨이**: Spring Cloud Gateway
- **서비스 디스커버리**: Spring Cloud Netflix Eureka
- **중앙 설정 관리**: Spring Cloud Config
- **인증/인가**: Spring Security, JWT (JSON Web Token)
- **데이터베이스**: JPA (Hibernate), QueryDSL, PostgreSQL
- **빌드 도구**: Gradle (Kotlin DSL)
- **API 문서화**: Springdoc (OpenAPI 3.0)

---

## 아키텍처 개요

본 프로젝트는 일반적인 MSA 구성 요소를 포함하고 있습니다.

```
+----------+      +-------------------+      +---------------------+
|          |      |                   |      |                     |
|  Client  |----->|  Gateway Service  |----->|  Discovery Service  |
|          |      | (Port: 8080)      |      |      (Eureka)       |
+----------+      +-------------------+      +---------------------+
                      |           ^
                      |           |
                      |           | Service Registration & Discovery
                      |           |
                      v           |
                  +-------------------+
                  |                   |
                  |   Core Service    |
                  | (Business Logic)  |
                  +-------------------+
```

### 서비스 역할

-   **`gateway-service`**: 시스템의 단일 진입점(Single Point of Entry) 역할을 하는 API 게이트웨이입니다. 다음과 같은 핵심 기능을 수행합니다.
    -   **라우팅**: 요청 경로에 따라 적절한 마이크로서비스로 요청을 전달합니다.
    -   **중앙 집중 인증**: 모든 요청에 대해 JWT 토큰을 검증하고, 인증된 사용자의 정보를 다운스트림 서비스로 전달합니다.
    -   **로드 밸런싱**: Eureka와 연동하여 서비스 인스턴스 간의 요청을 분산합니다.
    -   **API 문서 통합**: 각 마이크로서비스의 Swagger API 문서를 취합하여 단일 UI에서 제공합니다.

-   **`core-service`**: 실제 비즈니스 로직을 수행하는 핵심 마이크로서비스입니다. 회원 관리, 상품 관리 등 도메인별 로직이 이곳에 구현됩니다.

-   **`config-service`**: 모든 마이크로서비스의 설정 정보(DB 접속 정보, JWT Secret Key 등)를 중앙에서 관리하고 제공합니다.

-   **`discovery-service` (Eureka)**: 각 마이크로서비스가 자신의 위치를 등록하고, 다른 서비스를 찾을 수 있도록 돕는 서비스 레지스트리입니다.

---

## Core Service 내부 아키텍처

`core-service`는 **헥사고날 아키텍처(Hexagonal Architecture)**, 또는 포트와 어댑터(Ports and Adapters) 패턴의 원칙을 따릅니다. 이를 통해 비즈니스 로직을 외부 기술로부터 보호하고, 유연하고 테스트하기 쉬운 구조를 지향합니다.

-   **`domain` (Application Core)**
    -   **역할**: 순수한 비즈니스 로직의 집합. 이 프로젝트의 심장부입니다.
    -   **포함된 요소**:
        -   **Entities**: 비즈니스 데이터와 행위를 포함하는 핵심 도메인 객체. JPA 어노테이션(`@Entity`)이 포함되지만, 이는 DB 기술이 아닌 영속성을 위한 메타데이터로 간주합니다.
        -   **Repository Interfaces (Output Ports)**: 데이터 영속성을 위한 '요구사항'을 정의하는 인터페이스. (e.g., `MemberRepository`)
        -   **Services**: 비즈니스 로직을 오케스트레이션하고, 포트를 통해 외부와 통신합니다.
    -   **규칙**: **외부 계층(`api`, `storage`)에 대한 의존성을 갖지 않습니다.**

-   **`storage` (Driven/Output Adapter)**
    -   **역할**: `domain` 계층의 '요구사항(Port)'을 실제 기술로 '구현'합니다.
    -   **포함된 요소**:
        -   **Repository Implementations**: `domain`의 Repository 인터페이스를 Spring Data JPA를 사용하여 구현합니다.
        -   **JPA/QueryDSL 설정**: 데이터베이스 연결 및 쿼리 관련 설정을 담당합니다.
    -   **규칙**: `domain` 모듈을 참조하여 포트를 구현합니다.

-   **`api` (Driving/Input Adapter)**
    -   **역할**: 외부 세계(클라이언트)의 요청을 받아 `domain` 계층에 전달합니다.
    -   **포함된 요소**:
        -   **Controllers**: HTTP 요청을 처리하고 응답합니다.
        -   **Request/Response DTOs**: 외부와의 데이터 교환을 위한 객체입니다.
    -   **규칙**: `domain` 모듈을 참조하여 비즈니스 로직을 호출합니다.

-   **`common`**: 여러 모듈에서 공통으로 사용되는 유틸리티(e.g., `GlobalExceptionHandler`, `ResponseBuilder`)를 포함합니다.

### 의존성 규칙

> **`api` -> `domain` <- `storage`**

모든 의존성은 항상 외부에서 내부(`domain`)로 향합니다. 이를 통해 핵심 비즈니스 로직은 외부 기술의 변화에 영향을 받지 않습니다.

---

## 주요 기능 상세 설명

### 1. 인증 및 인가 흐름

API 게이트웨이를 통한 중앙 집중 인증 방식을 사용합니다.

1.  **토큰 발급**: 사용자가 로그인에 성공하면, `core-service`는 사용자 정보를 담은 JWT를 생성하여 클라이언트에게 전달합니다.
2.  **요청 및 토큰 검증**: 클라이언트는 이후 모든 요청의 `Authorization` 헤더에 `Bearer <JWT>`를 담아 게이트웨이로 보냅니다.
3.  **게이트웨이 필터**: `gateway-service`의 `AuthorizationFilter`가 JWT의 유효성을 검증합니다.
4.  **사용자 정보 전파**: 토큰이 유효하면, 게이트웨이는 토큰에서 사용자 ID를 추출하여 `X-USER-ID` 라는 커스텀 헤더에 담아 `core-service`로 요청을 전달합니다.
5.  **내부 인증 처리**: `core-service`의 `AuthenticationFilter`는 `X-USER-ID` 헤더를 읽어 Spring Security의 `SecurityContext`에 인증 객체를 생성합니다.
6.  **권한 활용**: 이제 `core-service`의 컨트롤러나 서비스에서는 `@AuthenticationPrincipal` 어노테이션을 통해 현재 로그인된 사용자의 ID를 쉽게 가져와 비즈니스 로직에 활용할 수 있습니다.

### 2. API 문서화 (Swagger)

MSA 환경에서의 API 문서화를 위해 게이트웨이에서 문서를 통합하는 방식을 사용합니다.

1.  **개별 서비스**: `core-service`는 자신의 API 명세(`v3/api-docs`)를 스스로 제공합니다.
2.  **게이트웨이**: `gateway-service`는 `springdoc-openapi-starter-webflux-ui`를 사용하여 통합된 Swagger UI를 제공합니다.
3.  **문서 취합**: 게이트웨이의 `application.yml`에 각 서비스의 `api-docs` 경로를 등록하면, 게이트웨이의 Swagger UI 페이지에서 드롭다운 메뉴를 통해 모든 서비스의 API 문서를 한 곳에서 조회하고 테스트할 수 있습니다.

## 실행 방법

1.  `config-service` 실행
2.  `discovery-service` (Eureka) 실행
3.  `core-service` 실행
4.  `gateway-service` 실행
5.  클라이언트는 `gateway-service`의 주소(`http://localhost:8080`)를 통해 시스템에 접근합니다.
