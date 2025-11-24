package dev.architecture.msaarchitectureskeleton.gateway.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationFilter : AbstractGatewayFilterFactory<AuthorizationFilter.Config>(Config::class.java) {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val headers = request.headers

            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED)
            }

            val authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION)
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return@GatewayFilter onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED)
            }

            val token = authorizationHeader.substring(7)
            log.info("token: {}", token) // Just logging the token for now

            // TODO: 토큰 유효성 검사 로직 추가 (e.g., an authentication service)

            chain.filter(exchange)
        }
    }

    private fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus): Mono<Void> {
        log.error(err)
        val response = exchange.response
        response.statusCode = httpStatus
        return response.setComplete()
    }

    class Config {
        // Configuration properties can be added here
    }
}
