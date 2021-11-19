package com.kotlin.test.kotlintest.global.security.authentication

import com.kotlin.test.kotlintest.domain.user.entity.UserFacade
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthDetailsService(
    private val userFacade: UserFacade
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val user = userFacade.getUserById(id.toLong())
        return AuthDetails(user = user)
    }
}