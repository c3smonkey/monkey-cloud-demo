package ch.c3smonkey.punaewele

import brave.sampler.Sampler
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.support.RequestContextUtils
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
class PunaeweleServiceApplication

fun main(args: Array<String>) {
    runApplication<PunaeweleServiceApplication>(*args)
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
class PunaeweleController(private val alohaService: AlohaService, private val inoaService: InoaService) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val locale = RequestContextUtils.getLocaleResolver(request)!!.resolveLocale(request).toLanguageTag()
        val aloah = StringBuilder().append(alohaService.getAloha(locale)).append(" ").append(inoaService.getInoa()).toString()
        LOG.info("Aloah: $aloah")
        LOG.info("Locale: $locale")
        return aloah
    }

    companion object {
        private val LOG = Logger.getLogger(PunaeweleController::class.java.name)
    }

}

/**
 *
 * RestTemplate
 *
 */
@Service
class AlohaService(private val restTemplate: RestTemplate) {
    companion object {
        private val URL = "http://aloha-service"
    }

    fun getAloha() = restTemplate.getForObject(URL, String::class.java)

    fun getAloha(locale: String): String? {
        return restTemplate.getForObject(StringBuilder().append(URL).append("/").append(locale).toString(), String::class.java)
    }
}


/**
 *
 * Feign
 *
 */
@Service
class InoaService(private val inoaFeignClient: InoaFeignClient) {
    fun getInoa() = inoaFeignClient.getInoa()
}

@FeignClient("inoa-service")
interface InoaFeignClient {

    @GetMapping(value = ["/"])
    fun getInoa(): String

}