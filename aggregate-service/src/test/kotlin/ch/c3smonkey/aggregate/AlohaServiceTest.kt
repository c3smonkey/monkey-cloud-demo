package ch.c3smonkey.aggregate

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["spring.cloud.config.enabled:false", "eureka.client.enabled:false"])
@AutoConfigureStubRunner(ids = ["ch.c3smonkey:aloha-service:+:stubs:0"])
@DirtiesContext
class AlohaServiceTest {

    @Autowired
    internal var alohaService: AlohaService? = null

    @Test
    @Throws(Exception::class)
    fun getGreeting() {
        Assert.assertEquals("Hello", alohaService!!.getAloha())
    }

    @Test
    @Throws(Exception::class)
    fun getGreetingWithLocale() {
        var greeting = alohaService!!.getAloha("en")
        Assert.assertEquals("Hello", greeting)
        greeting = alohaService!!.getAloha("es")
        Assert.assertEquals("Hola", greeting)
        greeting = alohaService!!.getAloha("de")
        Assert.assertEquals("Hallo", greeting)
    }
}


