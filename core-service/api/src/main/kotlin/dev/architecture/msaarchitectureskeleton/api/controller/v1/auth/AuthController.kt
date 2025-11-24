package dev.architecture.msaarchitectureskeleton.api.controller.v1.auth

import dev.architecture.msaarchitectureskeleton.common.controller.CommonResponse
import dev.architecture.msaarchitectureskeleton.common.controller.ResponseBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController {

    @GetMapping("/test")
    fun test(): ResponseEntity<CommonResponse<String>> {
        return ResponseBuilder.success("테스트 api 완료","테스트", status = HttpStatus.OK)
    }
}