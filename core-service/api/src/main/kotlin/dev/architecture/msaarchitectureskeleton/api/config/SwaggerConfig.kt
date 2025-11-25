package dev.architecture.msaarchitectureskeleton.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        // 게이트웨이를 통해 접속할 때 사용할 서버 정보
        val gatewayServer = Server().url("/core-service").description("Gateway Server")
        
        // core-service에 직접 접속할 때 사용할 서버 정보
        val localServer = Server().url("/").description("Local Server (Direct Access)")

        return OpenAPI()
            // 여러 서버 정보를 리스트로 추가합니다.
            .servers(listOf(gatewayServer, localServer))
            // API 기본 정보 설정
            .info(
                Info().title("Core Service API")
                    .description("Core 서비스의 API 명세서입니다.")
                    .version("v1.0.0")
            )
    }
}
