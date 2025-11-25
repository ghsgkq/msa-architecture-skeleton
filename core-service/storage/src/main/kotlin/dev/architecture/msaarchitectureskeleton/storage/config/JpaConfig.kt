package dev.architecture.msaarchitectureskeleton.storage.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EntityScan(basePackages = ["dev.architecture.msaarchitectureskeleton.domain"])
@EnableJpaRepositories(basePackages = ["dev.architecture.msaarchitectureskeleton.storage"])
@EnableTransactionManagement
@EnableJpaAuditing
class JpaConfig {
}