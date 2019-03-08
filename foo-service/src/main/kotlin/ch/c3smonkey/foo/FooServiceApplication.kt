package ch.c3smonkey.foo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest


@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(  AlohaProperties::class)
class AlohaServiceApplication

fun main(args: Array<String>) {
    runApplication<AlohaServiceApplication>(*args)
}

@ConfigurationProperties
class AlohaProperties {
    lateinit var greeting: String
    lateinit var greetings : Map<String, String>
}


@RestController
class AlohaController(val alohaProperties: AlohaProperties) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val greeting = alohaProperties.greeting
        LOG.info("Service [foo-service] say : $greeting")
        return greeting
    }

    @GetMapping("/{languageCode}")
    fun getWithLanguageCode(@PathVariable languageCode: String): String {
        LOG.info("Language Code: $languageCode")
        LOG.info("Greeting: " + alohaProperties.greetings.get(languageCode.toUpperCase()))
        return alohaProperties.greetings.getOrDefault(languageCode.toUpperCase(), alohaProperties.greeting)
    }

    companion object {
        private val LOG = Logger.getLogger(AlohaController::class.java.name)
    }


}
