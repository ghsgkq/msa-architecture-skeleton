package dev.architecture.msaarchitectureskeleton.storage.member

import com.querydsl.jpa.impl.JPAQueryFactory
import dev.architecture.msaarchitectureskeleton.domain.member.MemberDomainRepository
import dev.architecture.msaarchitectureskeleton.domain.member.dto.MemberAppend
import dev.architecture.msaarchitectureskeleton.domain.member.entity.MemberEntity
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val memberJpaRepository: MemberJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory
): MemberDomainRepository {

    override fun save(append: MemberAppend): MemberEntity {
        val entity = MemberEntity(
            memberId = append.memberId,
            memberPw = append.memberPw,
            name = append.name
        )
        return memberJpaRepository.save(entity)
    }
}