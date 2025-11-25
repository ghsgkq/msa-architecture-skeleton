package dev.architecture.msaarchitectureskeleton.api.controller.v1.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern


data class MemberAppend(
    @field:Schema(description = "사용자 id", nullable = false, required = true)
    @field:Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 4~20자의 영문, 숫자만 가능합니다.")
    val memberId: String,
    @field:Schema(description = "사용자 pw", nullable = false, required = true)
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
        message = "비밀번호는 8~20자의 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    val memberPw: String,
    @field:Schema(description = "사용지 이름", nullable = false, required = true)
    @field:Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$", message = "이름은 2~20자의 한글, 영문만 가능합니다.")
    val name: String
) {
    fun toDomain(): dev.architecture.msaarchitectureskeleton.domain.member.dto.MemberAppend {
        return dev.architecture.msaarchitectureskeleton.domain.member.dto.MemberAppend(
            memberId = this.memberId,
            memberPw = this.memberPw,
            name = this.name
        )
    }
}