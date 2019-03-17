package ch.c3smonkey.foo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.hateoas.Resource
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.RequestContextUtils
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest


@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(FooProperties::class)
@EnableHypermediaSupport(type = arrayOf(EnableHypermediaSupport.HypermediaType.HAL))
class FooServiceApplication

fun main(args: Array<String>) {
    // -noverify
    System.setProperty("spring.main.lazy-initialization", "true")
    runApplication<FooServiceApplication>(*args)
}

@RestController
class FooController(private val fooService: FooService) {

    @GetMapping(value = ["/foo"])
    fun foo(): Resource<Map<String, String>> {
        return Resource(fooService.getGreetings())
                .apply {
                    add(linkTo(methodOn(FooController::class.java).foo()).withSelfRel())
                }.also {
                    LOG.info("Foo is called.")
                }
    }

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val locale = RequestContextUtils.getLocaleResolver(request)!!.resolveLocale(request).toLanguageTag()
        val greeting = fooService.getGreetingsOrDefault(languageCode = locale)
        LOG.info("Service [foo-service] say : $greeting")
        return greeting
    }

    @GetMapping("/{languageCode}")
    fun getWithLanguageCode(@PathVariable languageCode: String): String {
        LOG.info("Language Code: $languageCode")
        LOG.info("Greeting: " + fooService.getGreetings(languageCode))
        return fooService.getGreetingsOrDefault(languageCode)
    }

    companion object {
        private val LOG = Logger.getLogger(FooController::class.java.name)
    }
}

@Service
class FooService(val fooProperties: FooProperties) {
    fun getGreetings(): Map<String, String> =
            fooProperties.greetings

    fun getGreetings(languageCode: String): String? {
        Thread.sleep(2_000)
        return fooProperties.greetings.get(languageCode.toUpperCase())
    }

    fun getGreetingsOrDefault(languageCode: String) =
            fooProperties.greetings.getOrDefault(languageCode.toUpperCase(), fooProperties.greeting)


}

@ConfigurationProperties
class FooProperties {
    lateinit var greeting: String
    lateinit var greetings: Map<String, String>
}

