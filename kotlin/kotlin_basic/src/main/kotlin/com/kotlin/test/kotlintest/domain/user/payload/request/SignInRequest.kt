package com.kotlin.test.kotlintest.domain.user.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignInRequest(

    @NotBlank
    @Email
    val email: String,

    @NotBlank
    @Size(min = 6)
    val password: String,

    @NotBlank
    val name: String

)