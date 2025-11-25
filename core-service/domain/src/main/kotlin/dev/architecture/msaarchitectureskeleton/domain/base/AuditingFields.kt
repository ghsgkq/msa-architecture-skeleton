package dev.architecture.msaarchitectureskeleton.domain.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class AuditingFields {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    var updateAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = true)
    var deleteAt: LocalDateTime? = null
}
