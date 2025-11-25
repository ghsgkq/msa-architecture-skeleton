package dev.architecture.msaarchitectureskeleton.api.controller.v1.auth.dto.response

import dev.architecture.msaarchitectureskeleton.domain.member.entity.MemberEntity
import java.time.LocalDateTime
import java.util.UUID

data class Member(
    val id: UUID,
    val memberId: String,
    val memberPw: String,
    val name: String,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime?,
    val deleteAt: LocalDateTime?
){
    companion object {
        fun fromDomain(member: MemberEntity): Member {
            return Member(
                id = member.getId(),
                memberId = member.memberId,
                memberPw = member.memberPw,
                name = member.name,
                createAt = member.createAt,
                updateAt = member.updateAt,
                deleteAt = member.deleteAt
            )
        }
    }
}