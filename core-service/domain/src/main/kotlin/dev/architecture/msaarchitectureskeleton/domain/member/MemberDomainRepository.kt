package dev.architecture.msaarchitectureskeleton.domain.member

import dev.architecture.msaarchitectureskeleton.domain.member.dto.MemberAppend
import dev.architecture.msaarchitectureskeleton.domain.member.entity.MemberEntity

interface MemberDomainRepository {
    fun save(append: MemberAppend): MemberEntity
}
