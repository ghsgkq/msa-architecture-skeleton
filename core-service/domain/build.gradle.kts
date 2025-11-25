group = "dev.architecture"
version = "0.0.1-SNAPSHOT"
description = "msa-architecture-skeleton"

dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.f4b6a3:ulid-creator:5.2.3")

    // @Comment 어노테이션 등을 사용하기 위해 추가
    // 버전은 io.spring.dependency-management 플러그인이 자동으로 관리
    implementation("org.hibernate.orm:hibernate-core")
}
