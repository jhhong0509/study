package com.kotlin.test.kotlintest.global.security

import com.kotlin.test.kotlintest.global.exception.ExceptionHandlerFilter
import com.kotlin.test.kotlintest.global.security.authentication.JwtTokenFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


class FilterConfig(
    private val tokenFilter: JwtTokenFilter,
    private val exceptionFilter: ExceptionHandlerFilter
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, JwtTokenFilter::class.java)
    }

}