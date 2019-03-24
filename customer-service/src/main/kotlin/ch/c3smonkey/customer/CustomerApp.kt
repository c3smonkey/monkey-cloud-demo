package ch.c3smonkey.customer

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assertThat
 import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.hateoas.*
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.hateoas.config.EnableHypermediaSupport


@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(CustomerProperties::class)
@EnableHypermediaSupport(type = arrayOf(EnableHypermediaSupport.HypermediaType.HAL))
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
class CustomerService(val customerProperties: CustomerProperties) {
    fun getName() = customerProperties.name
    fun getNames() = customerProperties.names
}


@ConfigurationProperties
class CustomerProperties {
    lateinit var name: String
    lateinit var names: Map<String, String>

}

//   _   _    _  _____ _____ ___    _    ____
//  | | | |  / \|_   _| ____/ _ \  / \  / ___|
//  | |_| | / _ \ | | |  _|| | | |/ _ \ \___ \
//  |  _  |/ ___ \| | | |___ |_| / ___ \ ___) |
//  |_| |_/_/   \_\_| |_____\___/_/   \_\____/
//
class CustomerModel(
        val firstname: String,
        val lastname: String
) : RepresentationModel<CustomerModel>()


@RequestMapping("/api")
@RestController
class CustomerHalController() {

    @GetMapping(value = ["/hal"], produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
    fun hal(): CustomerModel {
        return fooBarFunction()
    }
    @GetMapping(value = ["/uber"], produces = [MediaTypes.UBER_JSON_VALUE])
    fun uber(): CustomerModel {
        return fooBarFunction()
    }
    @GetMapping(value = ["/collection"], produces = [MediaTypes.COLLECTION_JSON_VALUE])
    fun collection(): CustomerModel {
        return fooBarFunction()
    }
    @GetMapping(value = ["/alps"], produces = [MediaTypes.ALPS_JSON_VALUE])
    fun alps(): CustomerModel {
        return fooBarFunction()
    }
    @GetMapping(value = ["/halforms"], produces = [MediaTypes.HAL_FORMS_JSON_VALUE])
    fun halForms(): CustomerModel {
        return fooBarFunction()
    }


    private fun fooBarFunction(): CustomerModel {
        val model = CustomerModel(firstname = "Dave", lastname = "Matthews")
        model.add(Link("http://myhost/people/42"))

        var link = Link("/something")
        assertThat(link.href, equalTo("/something"))
        assertThat(link.rel, equalTo(IanaLinkRelations.SELF))


        link = Link("/something", "my-rel")
        assertThat(link.href, equalTo("/something"))
        assertThat(link.rel, equalTo(LinkRelation.of("my-rel")))

        link = Link("/{segment}/something{?parameter}")
        assert(link.isTemplated())
        assert(link.variableNames.containsAll(listOf("segment", "parameter")))
        val values = mapOf("segment" to "path", "parameter" to 42)
        assert(link.expand(values).href.equals("/path/something?parameter=42"))

        return model
    }

}