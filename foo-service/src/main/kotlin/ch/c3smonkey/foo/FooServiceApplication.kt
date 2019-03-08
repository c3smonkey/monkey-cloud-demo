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
@EnableConfigurationProperties(FooProperties::class)
class FooServiceApplication

fun main(args: Array<String>) {
    runApplication<FooServiceApplication>(*args)
}

@ConfigurationProperties
class FooProperties {
    lateinit var greeting: String
    lateinit var greetings: Map<String, String>
}


@RestController
class FooController(val fooProperties: FooProperties) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val greeting = fooProperties.greeting
        LOG.info("Service [foo-service] say : $greeting")
        return greeting
    }

    @GetMapping("/{languageCode}")
    fun getWithLanguageCode(@PathVariable languageCode: String): String {
        LOG.info("Language Code: $languageCode")
        LOG.info("Greeting: " + fooProperties.greetings.get(languageCode.toUpperCase()))
        return fooProperties.greetings.getOrDefault(languageCode.toUpperCase(), fooProperties.greeting)
    }

    companion object {
        private val LOG = Logger.getLogger(FooController::class.java.name)
    }


}
