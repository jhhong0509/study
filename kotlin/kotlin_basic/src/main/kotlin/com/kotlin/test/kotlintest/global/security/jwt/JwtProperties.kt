package com.kotlin.test.kotlintest.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.util.*

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var secretKey: String,
    val accessExp: Long,
    val refreshExp: Long
) {
    init {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }
}