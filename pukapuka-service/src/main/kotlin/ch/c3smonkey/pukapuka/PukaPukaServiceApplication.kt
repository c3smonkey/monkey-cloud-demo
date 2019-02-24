package ch.c3smonkey.pukapuka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PukaPukaServiceApplication

fun main(args: Array<String>) {
    runApplication<PukaPukaServiceApplication>(*args)
}
