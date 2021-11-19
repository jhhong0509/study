package com.kotlin.test.kotlintest.domain.user.entity

import com.kotlin.test.kotlintest.domain.post.entity.Board
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    @Column(unique = true)
    val email: String,

    val password: String,

    @OneToMany(mappedBy = "writer")
    val boards: MutableList<Board> = mutableListOf()

) {

    override fun equals(other: Any?): Boolean {
        return if (other is User) {
            other.id == id
        } else false
    }
}