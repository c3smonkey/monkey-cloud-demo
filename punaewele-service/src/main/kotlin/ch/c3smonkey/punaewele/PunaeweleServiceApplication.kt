package ch.c3smonkey.punaewele

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.RequestContextUtils
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableDiscoveryClient
class PunaeweleServiceApplication

fun main(args: Array<String>) {
    runApplication<PunaeweleServiceApplication>(*args)
}

@RestController
class PunaeweleController() {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val locale = RequestContextUtils.getLocaleResolver(request)!!.resolveLocale(request).toLanguageTag()
//        val greeting = StringBuilder().append(greetingService.getGreeting(locale)).append(" ").append(nameService.getName()).toString()
//        LOG.info("Greeting: $greeting")
        LOG.info("Locale: $locale")
        return "aloha"
    }

    companion object {
        private val LOG = Logger.getLogger(PunaeweleController::class.java.name)
    }

}



