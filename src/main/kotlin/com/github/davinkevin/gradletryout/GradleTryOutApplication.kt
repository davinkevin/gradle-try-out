package com.github.davinkevin.gradletryout

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GradleTryOutApplication

fun main(args: Array<String>) {
    runApplication<GradleTryOutApplication>(*args)
}
