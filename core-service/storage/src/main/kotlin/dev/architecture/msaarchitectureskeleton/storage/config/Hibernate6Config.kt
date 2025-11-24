package dev.architecture.msaarchitectureskeleton.storage.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Hibernate6Config {
    @Bean
    fun hibernate6Module(): Hibernate6Config {
        return Hibernate6Config()
    }
}