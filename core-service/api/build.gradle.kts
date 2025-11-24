group = "dev.architecture"
version = "0.0.1-SNAPSHOT"
description = "msa-architecture-skeleton"

dependencies {
    implementation(project(":core-service:domain"))
    implementation(project(":core-service:storage"))
    implementation(project(":core-service:common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    implementation("io.jsonwebtoken:jjwt:0.13.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.13.0")
    implementation("io.jsonwebtoken:jjwt-jackson:0.13.0")

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

