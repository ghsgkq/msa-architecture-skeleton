package dev.architecture.msaarchitectureskeleton.api.controller.v1.auth

import dev.architecture.msaarchitectureskeleton.api.controller.v1.auth.dto.request.MemberAppend
import dev.architecture.msaarchitectureskeleton.api.controller.v1.auth.dto.response.Member
import dev.architecture.msaarchitectureskeleton.common.controller.CommonResponse
import dev.architecture.msaarchitectureskeleton.common.controller.ResponseBuilder
import dev.architecture.msaarchitectureskeleton.domain.member.MemberService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val memberService: MemberService
) {

    @GetMapping("/test")
    @Operation(summary = "테스트 api")
    fun test(): ResponseEntity<CommonResponse<String>> {
        return ResponseBuilder.success("테스트 api 완료", "테스트", status = HttpStatus.OK)
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    fun register(
        @RequestBody @Valid request: MemberAppend
    ): ResponseEntity<CommonResponse<Member>> {
        val member = memberService.append(request.toDomain())
        val response = member.let { Member.fromDomain(it) }
        return ResponseBuilder.success(response, "회원가입이 완료되었습니다.", HttpStatus.CREATED)
    }
}
