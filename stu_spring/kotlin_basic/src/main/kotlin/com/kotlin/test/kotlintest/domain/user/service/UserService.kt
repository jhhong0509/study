package com.kotlin.test.kotlintest.domain.user.service

import com.kotlin.test.kotlintest.domain.user.entity.User
import com.kotlin.test.kotlintest.domain.user.entity.UserRepository
import com.kotlin.test.kotlintest.domain.user.payload.request.SignUpRequest
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun signUp(signUpRequest: SignUpRequest) {
        val user = buildUser(signUpRequest)
        userRepository.save(user)
    }

    private fun buildUser(signUpRequest: SignUpRequest): User {
        return User(
            name = signUpRequest.name,
            email = signUpRequest.email,
            password = signUpRequest.password
        )
    }

}