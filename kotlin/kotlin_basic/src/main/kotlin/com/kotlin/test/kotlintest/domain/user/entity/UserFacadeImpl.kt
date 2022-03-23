package com.kotlin.test.kotlintest.domain.user.entity

import com.kotlin.test.kotlintest.domain.user.exceptions.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacadeImpl(
    private val userRepository: UserRepository
) : UserFacade {

    override fun getUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException.EXCEPTION
    }

    override fun getCurrentUser(): User {
        val id = getUsername()
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException.EXCEPTION
    }

    private fun getUsername(): Long {
        val authentication = SecurityContextHolder.getContext()
            .authentication
        return authentication.name.toLong()
    }

}