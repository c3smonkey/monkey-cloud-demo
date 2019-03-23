package ch.c3smonkey.customer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(CustomerProperties::class)
class CustomerApp

fun main(args: Array<String>) {
    System.setProperty("spring.main.lazy-initialization", "true") // -noverify
    runApplication<CustomerApp>(*args)
}


@RestController
class CustomerController(val customerService: CustomerService) {

    @GetMapping(value = ["/"])
    fun index(request: HttpServletRequest): String {
        val name = customerService.getName()
        LOG.info("Service [customer-service] say : $name")
        return name
    }

    @GetMapping(value = ["/name"])
    fun getName() = customerService.getName()

    @GetMapping(value = ["/names"])
    fun getNames() = customerService.getNames()

    companion object {
        private val LOG = Logger.getLogger(CustomerController::class.java.name)
    }
}

@Service
class CustomerService(val customerProperties: CustomerProperties){
    fun getName() = customerProperties.name
    fun getNames() = customerProperties.names
}




@ConfigurationProperties
class CustomerProperties {
    lateinit var name: String
    lateinit var names : Map<String, String>

}