package dev.architecture.msaarchitectureskeleton.domain.member

import dev.architecture.msaarchitectureskeleton.domain.member.dto.MemberAppend
import dev.architecture.msaarchitectureskeleton.domain.member.entity.MemberEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val passwordEncoder: PasswordEncoder,
    private val memberAppender: MemberAppender
) {

    fun append(dto: MemberAppend): MemberEntity {
        dto.encode(passwordEncoder.encode(dto.memberPw))
        return memberAppender.append(command = dto)
    }
}
