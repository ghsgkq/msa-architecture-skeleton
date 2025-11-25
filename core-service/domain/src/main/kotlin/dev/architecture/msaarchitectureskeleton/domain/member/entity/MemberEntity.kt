package dev.architecture.msaarchitectureskeleton.domain.member.entity

import dev.architecture.msaarchitectureskeleton.domain.base.PrimaryKey
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Comment("사용자 테이블")
@Table(name = "member")
@Entity
class MemberEntity(
    memberId: String,
    memberPw: String,
    name: String
): PrimaryKey() {
    @Comment("로그인 id")
    @Column(name = "member_id",nullable = false, unique = true)
    var memberId: String = memberId
        protected set

    @Comment("로그인 pw")
    @Column(name = "member_pw",nullable = false)
    var memberPw: String = memberPw
        protected set

    @Comment("사용자 이름")
    @Column(name = "name",nullable = false)
    var name: String = name
        protected set

}