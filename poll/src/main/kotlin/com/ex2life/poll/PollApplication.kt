package com.ex2life.poll

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PollApplication

fun main(args: Array<String>) {
	runApplication<PollApplication>(*args)
}
