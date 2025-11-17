group = "dev.architecture"
version = "0.0.1-SNAPSHOT"
description = "msa-architecture-skeleton"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.2.21")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    // 👇 이 줄을 추가: XStream 버전을 최신(1.4.21)으로 강제 지정
    implementation("com.thoughtworks.xstream:xstream:1.4.21")
}

tasks.getByName("bootJar") {
    enabled = true
}


tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

