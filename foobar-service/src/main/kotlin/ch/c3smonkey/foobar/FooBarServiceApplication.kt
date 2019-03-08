package ch.c3smonkey.foobar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.support.RequestContextUtils
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class FooBarServiceApplication

fun main(args: Array<String>) {
    runApplication<FooBarServiceApplication>(*args)
}


@Configuration
class WebConfiguration {

    @LoadBalanced
    @Bean
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder.build()
    }


}


@RestController
class FooBarController(private val fooService: FooService, private val barService: BarService) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val locale = RequestContextUtils.getLocaleResolver(request)!!.resolveLocale(request).toLanguageTag()
        val foobar = StringBuilder().append(fooService.getGreeting(locale)).append(" ").append(barService.getName()).toString()
        LOG.info("Greeting: $foobar")
        LOG.info("Locale: $locale")
        return foobar
    }

    companion object {
        private val LOG = Logger.getLogger(FooBarController::class.java.name)
    }

}

/**
 * RestTemplate
 */
@Service
class FooService(private val restTemplate: RestTemplate) {

    fun getGreeting() = restTemplate.getForObject(URL, String::class.java)

    fun getGreeting(locale: String): String? {
        return restTemplate.getForObject(StringBuilder().append(URL).append("/").append(locale).toString(), String::class.java)
    }
    companion object {
        private val URL = "http://foo-service"
    }
}


/**
 * Feign
 */
@Service
class BarService(private val inoaFeignClient: BarFeignClient) {
    fun getName() = inoaFeignClient.getName()
}

@FeignClient("bar-service")
interface BarFeignClient {

    @GetMapping(value = ["/"])
    fun getName(): String

}