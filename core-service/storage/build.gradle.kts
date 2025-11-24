group = "dev.architecture"
version = "0.0.1-SNAPSHOT"
description = "msa-architecture-skeleton"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.github.f4b6a3:ulid-creator:5.2.3")
    runtimeOnly("org.postgresql:postgresql")

    // QueryDSL
    implementation("io.github.openfeign.querydsl:querydsl-jpa:7.0")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:7.0:jpa")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate6:2.17.0")
}



// Q-Class 생성 위치 설정 (선택사항이지만 권장)
val generated = "build/generated/sources/annotationProcessor/kotlin/main"

sourceSets {
    getByName("main") {
        java {
            srcDir(generated)
        }
    }
}

// clean 작업 시 generated 디렉토리도 삭제
tasks.named<Delete>("clean") {
    delete(generated)
}