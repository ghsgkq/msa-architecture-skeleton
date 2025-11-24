package dev.architecture.msaarchitectureskeleton.gateway.routes

import dev.architecture.msaarchitectureskeleton.gateway.filter.AuthorizationFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreRoutesConfig(
    private val authorizationFilter: AuthorizationFilter
) {

    @Bean
    fun coreRouteLocator(builder: RouteLocatorBuilder): RouteLocator =
        builder.routes()
            .route("core-service-auth") { route ->
                route.path("/core-service/api/*/auth/**")
                    .filters { filter ->
                        filter.rewritePath("/core-service/(?<segment>.*)", $$"/${segment}")
                    }
                    .uri("lb://CORE-SERVICE")
            }
            .route("core-service") { route ->
                route.path("/core-service/**")
                    .filters { filter ->
                        filter.filter(authorizationFilter.apply(AuthorizationFilter.Config()))
                        filter.rewritePath("/core-service/(?<segment>.*)", $$"/${segment}")
                    }
                    .uri("lb://CORE-SERVICE")
            }
            .build()
}
