package dev.architecture.msaarchitectureskeleton.entity.member

import dev.architecture.msaarchitectureskeleton.entity.base.PrimaryKey
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Comment("사용자 테이블")
@Table(name = "member")
@Entity
class MemberEntity(
    userId: String,
    userPw: String,
    name: String
): PrimaryKey() {
    @Comment("로그인 id")
    @Column(name = "member_id",nullable = false, unique = true)
    var userId: String = userId
        protected set

    @Comment("로그인 pw")
    @Column(name = "member_pw",nullable = false)
    var userPw: String = userPw
        protected set

    @Comment("사용자 이름")
    @Column(name = "name",nullable = false)
    var name: String = name
        protected set

}