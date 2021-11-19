package com.kotlin.test.kotlintest.domain.user.service

import com.kotlin.test.kotlintest.domain.user.entity.User
import com.kotlin.test.kotlintest.domain.user.entity.UserRepository
import com.kotlin.test.kotlintest.domain.user.exceptions.PasswordNotMatchException
import com.kotlin.test.kotlintest.domain.user.exceptions.UserAlreadyExistException
import com.kotlin.test.kotlintest.domain.user.exceptions.UserNotFoundException
import com.kotlin.test.kotlintest.domain.user.payload.request.SignInRequest
import com.kotlin.test.kotlintest.domain.user.payload.request.SignUpRequest
import com.kotlin.test.kotlintest.domain.user.payload.response.TokenResponse
import com.kotlin.test.kotlintest.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {

    fun signUp(signUpRequest: SignUpRequest) {
        userRepository.findByEmail(signUpRequest.email)?.let { throw UserAlreadyExistException.EXCEPTION }

        buildUser(signUpRequest)
            .let { user -> userRepository.save(user) }

    }

    fun signIn(request: SignInRequest): TokenResponse {

        return userRepository.findByEmail(request.email)?.let { user ->
            if (!passwordEncoder.matches(request.password, user.password)) {
                throw PasswordNotMatchException.EXCEPTION
            }

            val accessToken = jwtTokenProvider.generateAccessToken(user.id.toString())
            val refreshToken = jwtTokenProvider.generateRefreshToken(user.id.toString())

            TokenResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } ?: throw UserNotFoundException.EXCEPTION
    }

    private fun buildUser(signUpRequest: SignUpRequest): User {
        return User(
            name = signUpRequest.name,
            email = signUpRequest.email,
            password = passwordEncoder.encode(signUpRequest.password)
        )
    }

}