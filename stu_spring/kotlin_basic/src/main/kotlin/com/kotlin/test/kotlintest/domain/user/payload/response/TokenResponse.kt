package com.kotlin.test.kotlintest.domain.user.payload.response

import com.fasterxml.jackson.annotation.JsonProperty

class TokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("refresh_token")
    val refreshToken: String
)