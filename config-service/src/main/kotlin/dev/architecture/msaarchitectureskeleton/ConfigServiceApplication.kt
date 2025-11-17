package dev.architecture.msaarchitectureskeleton

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@EnableConfigServer
@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = ["dev.architecture.msaarchitectureskeleton"])
class ConfigServiceApplication

fun main(args: Array<String>) {
    runApplication<ConfigServiceApplication>(*args)
}
