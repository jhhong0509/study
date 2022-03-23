package com.kotlin.test.kotlintest.global.security.jwt

import com.kotlin.test.kotlintest.global.exception.payload.UnexpectedException
import com.kotlin.test.kotlintest.global.security.exceptions.InvalidTokenException
import com.kotlin.test.kotlintest.global.security.exceptions.TokenExpiredException
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    val userDetailsService: UserDetailsService,
    val jwtProperties: JwtProperties,
) {

    companion object {
        const val TOKEN_TYPE_ACCESS: String = "access"
        const val TOKEN_TYPE_REFRESH: String = "refresh"
        const val TOKEN_HEADER: String = "Authorization"
        const val TOKEN_TYPE_PREFIX: String = "Bearer "
    }

    fun generateAccessToken(subject: String): String {
        return generateToken(subject, jwtProperties.accessExp, TOKEN_TYPE_ACCESS)
    }

    fun generateRefreshToken(subject: String): String {
        return generateToken(subject, jwtProperties.refreshExp, TOKEN_TYPE_REFRESH)
    }

    fun parseToken(request: HttpServletRequest): String? {
        val token: String? = request.getHeader(TOKEN_HEADER)
        return token?.replace(TOKEN_TYPE_PREFIX, "")
    }

    fun authenticateUser(token: String?): Authentication? {
        return token?.let { it ->
            val claims = parseToken(it)
            val userDetails = userDetailsService.loadUserByUsername(claims.subject)
            UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
        }
    }

    private fun parseToken(token: String): Claims {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey)
                .parseClaimsJws(token).body
        } catch (e: JwtException) {
            when (e) {
                is ExpiredJwtException -> throw TokenExpiredException.EXCEPTION
                is SignatureException, is MalformedJwtException -> throw InvalidTokenException.EXCEPTION
                else -> throw UnexpectedException.EXCEPTION
            }
        }
    }

    private fun generateToken(subject: String, expiration: Long, type: String): String {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(Date(System.currentTimeMillis() + expiration * 1000))
            .setIssuedAt(Date())
            .setHeaderParam("typ", type)
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secretKey)
            .compact()
    }

}