package dev.architecture.msaarchitectureskeleton.gateway.filter

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class AuthorizationFilter(
    @Value("\${jwt.secret}") private val secretKey: String
) : AbstractGatewayFilterFactory<AuthorizationFilter.Config>(Config::class.java) {

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

            val userId = getUserId(token)
            if (userId == null) {
                return@GatewayFilter onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }

            val newRequest = exchange.request.mutate()
                .header("X-USER-ID", userId)
                .build()

            chain.filter(exchange.mutate().request(newRequest).build())
        }
    }

    private fun getUserId(token: String): String? {
        return try {
            val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
            claims.subject
        } catch (e: Exception) {
            log.error("JWT token validation error: {}", e.message)
            null
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
