package ch.c3smonkey.aloha

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.RequestContextUtils
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
    lateinit var aloha: String
    lateinit var alohas : Map<String, String>
}


@RestController
class AlohaController(val alohaProperties: AlohaProperties) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val aloha = alohaProperties.aloha
        LOG.info("Service [aloha-service] say : $aloha")
        return aloha
    }

    @GetMapping("/{languageCode}")
    fun getWithLanguageCode(@PathVariable languageCode: String): String {
        LOG.info("Language Code: $languageCode")
        LOG.info("Greeting: " + alohaProperties.alohas.get(languageCode.toUpperCase()))
        return alohaProperties.alohas.getOrDefault(languageCode.toUpperCase(), alohaProperties.aloha)
    }

    companion object {
        private val LOG = Logger.getLogger(AlohaController::class.java.name)
    }


}
