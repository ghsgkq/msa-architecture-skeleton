package dev.architecture.msaarchitectureskeleton.domain.member.dto

data class MemberAppend(
    val memberId: String,
    val memberPw: String,
    val name: String
) {

    fun encode(memberPw: String): MemberAppend {
        return MemberAppend(
            memberId = this.memberId,
            memberPw = memberPw,
            name = this.name
        )
    }
}