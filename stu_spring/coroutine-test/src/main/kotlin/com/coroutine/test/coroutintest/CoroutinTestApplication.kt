package com.coroutine.test.coroutintest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoroutinTestApplication

fun main(args: Array<String>) {
    runApplication<CoroutinTestApplication>(*args)
}
