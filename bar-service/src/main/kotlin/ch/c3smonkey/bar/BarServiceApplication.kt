package ch.c3smonkey.bar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(BarProperties::class)
class BarServiceApplication

fun main(args: Array<String>) {
    runApplication<BarServiceApplication>(*args)
}

@ConfigurationProperties
class BarProperties {
    lateinit var name: String
    lateinit var names : Map<String, String>

}


@RestController
class BarController(val barProperties: BarProperties) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val name = barProperties.name
        LOG.info("Service [bar-service] say : $name")
        return name
    }

    companion object {
        private val LOG = Logger.getLogger(BarController::class.java.name)
    }


}
