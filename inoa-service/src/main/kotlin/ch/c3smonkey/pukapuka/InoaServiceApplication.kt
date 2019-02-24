package ch.c3smonkey.pukapuka

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
@EnableConfigurationProperties(InoaProperties::class)
class InoaServiceApplication

fun main(args: Array<String>) {
    runApplication<InoaServiceApplication>(*args)
}

@ConfigurationProperties
class InoaProperties {
    lateinit var inoa: String
}


@RestController
class InoaController(val inoaProperties: InoaProperties) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val inoa = inoaProperties.inoa
        LOG.info("Service [inoa-service] say : $inoa")
        return inoa
    }

    companion object {
        private val LOG = Logger.getLogger(InoaController::class.java.name)
    }


}
