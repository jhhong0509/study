package com.kotlin.test.kotlintest.global

import com.kotlin.test.kotlintest.domain.user.entity.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class BootStrap(
    private val userRepository: UserRepository
) : CommandLineRunner{

    override fun run(vararg args: String?) {
        userRepository.deleteAll();
    }
}