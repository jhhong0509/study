package com.kotlin.test.kotlintest.global.security

import com.kotlin.test.kotlintest.global.exception.ExceptionHandlerFilter
import com.kotlin.test.kotlintest.global.security.authentication.JwtTokenFilter
import com.kotlin.test.kotlintest.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val exceptionHandlerFilter = ExceptionHandlerFilter()
        val tokenFilter = JwtTokenFilter(jwtTokenProvider)

        http
            .cors().and()
            .csrf().disable()

            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.POST, "/user").permitAll()
            .antMatchers(HttpMethod.GET, "/auth").permitAll()
            .antMatchers(HttpMethod.GET, "/posts/list").permitAll()
            .anyRequest().authenticated()
            .and().apply(FilterConfig(tokenFilter, exceptionHandlerFilter))

    }

    @Bean
    fun paswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}