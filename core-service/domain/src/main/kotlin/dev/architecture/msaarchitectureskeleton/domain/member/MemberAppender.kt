package dev.architecture.msaarchitectureskeleton.domain.member

import dev.architecture.msaarchitectureskeleton.domain.member.dto.MemberAppend
import dev.architecture.msaarchitectureskeleton.domain.member.entity.MemberEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class MemberAppender(
    private val memberDomainRepository: MemberDomainRepository
) {
    @Transactional
    fun append(
        command: MemberAppend
    ): MemberEntity {
        return memberDomainRepository.save(append = command)
    }
}