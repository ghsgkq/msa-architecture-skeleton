group = "dev.architecture"
version = "0.0.1-SNAPSHOT"
description = "msa-architecture-skeleton"

dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework:spring-web")
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

