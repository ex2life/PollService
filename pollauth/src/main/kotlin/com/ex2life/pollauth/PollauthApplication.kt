package com.ex2life.pollauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
class PollauthApplication

fun main(args: Array<String>) {
	runApplication<PollauthApplication>(*args)
}
