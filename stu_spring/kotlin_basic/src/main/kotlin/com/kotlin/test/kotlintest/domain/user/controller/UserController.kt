package com.kotlin.test.kotlintest.domain.user.controller

import com.kotlin.test.kotlintest.domain.user.payload.request.SignUpRequest
import com.kotlin.test.kotlintest.domain.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/user")
    fun signUp(@RequestBody signUpRequest: SignUpRequest) {
        userService.signUp(signUpRequest)
    }

}