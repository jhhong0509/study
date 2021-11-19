package com.kotlin.test.kotlintest.domain.user.entity

import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var name: String,

    @Column(unique = true)
    var email: String,

    var password: String

) {

    override fun equals(other: Any?): Boolean {
        return if (other is User) {
            other.id == id
        } else false
    }
}