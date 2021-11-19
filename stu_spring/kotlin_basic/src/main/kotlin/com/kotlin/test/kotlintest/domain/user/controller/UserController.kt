package com.kotlin.test.kotlintest.domain.user.controller

import com.kotlin.test.kotlintest.domain.user.payload.request.SignInRequest
import com.kotlin.test.kotlintest.domain.user.payload.request.SignUpRequest
import com.kotlin.test.kotlintest.domain.user.payload.response.TokenResponse
import com.kotlin.test.kotlintest.domain.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/user")
    fun signUp(@RequestBody @Valid signUpRequest: SignUpRequest) {
        userService.signUp(signUpRequest)
    }

    @PostMapping("/auth")
    fun signUp(@RequestBody @Valid signInRequest: SignInRequest): TokenResponse {
        return userService.signIn(signInRequest)
    }

}