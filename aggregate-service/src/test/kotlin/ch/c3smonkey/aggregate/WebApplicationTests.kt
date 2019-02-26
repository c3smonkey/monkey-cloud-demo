package ch.c3smonkey.aggregate

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["spring.cloud.config.enabled:false", "eureka.client.enabled:false"])
@AutoConfigureStubRunner(ids = ["ch.c3smonkey:inoa-service:+:stubs:0", "ch.c3smonkey:aloha-service:+:stubs:0"])
@DirtiesContext
class WebApplicationTests {

    @Autowired
    internal var rest: TestRestTemplate? = null

    @Test
    fun contextLoads() {
        val headers = HttpHeaders()
        headers.add("Accept-Language", "es")
        val entity = HttpEntity("parameters", headers)
        val greetingResp = rest!!.exchange("/", HttpMethod.GET, entity, String::class.java)
        assertEquals("Hola John", greetingResp.body)
    }
}
