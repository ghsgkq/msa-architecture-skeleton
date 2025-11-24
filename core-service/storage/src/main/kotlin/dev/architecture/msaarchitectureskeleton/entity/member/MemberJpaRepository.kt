package dev.architecture.msaarchitectureskeleton.entity.member

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberJpaRepository: JpaRepository<MemberEntity, UUID> {
}