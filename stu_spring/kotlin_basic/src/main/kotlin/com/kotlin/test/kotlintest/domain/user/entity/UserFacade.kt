package com.kotlin.test.kotlintest.domain.user.entity

interface UserFacade {
    fun getUserById(id: Long): User
    fun getCurrentUser(): User
}