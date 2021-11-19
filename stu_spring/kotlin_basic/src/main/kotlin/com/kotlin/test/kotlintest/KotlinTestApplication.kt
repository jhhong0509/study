package com.kotlin.test.kotlintest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
class KotlinTestApplication

fun main(args: Array<String>) {
	runApplication<KotlinTestApplication>(*args)
}
