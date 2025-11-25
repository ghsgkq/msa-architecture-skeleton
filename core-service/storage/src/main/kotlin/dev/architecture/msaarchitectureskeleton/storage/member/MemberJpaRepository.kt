package dev.architecture.msaarchitectureskeleton.storage.member

import dev.architecture.msaarchitectureskeleton.domain.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberJpaRepository: JpaRepository<MemberEntity, UUID> {
}