package ch.c3smonkey.foobar

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
@AutoConfigureStubRunner(ids = ["ch.c3smonkey:foo-service:+:stubs:0"])
@DirtiesContext
class FooServiceTest {

    @Autowired
    internal var fooService: FooService? = null

    @Test
    @Throws(Exception::class)
    fun getGreeting() {
        Assert.assertEquals("Hello", fooService!!.getGreeting())
    }

    @Test
    @Throws(Exception::class)
    fun getGreetingWithLocale() {
        var greeting = fooService!!.getGreeting("en")
        Assert.assertEquals("Hello", greeting)
        greeting = fooService!!.getGreeting("es")
        Assert.assertEquals("Hola", greeting)
        greeting = fooService!!.getGreeting("de")
        Assert.assertEquals("Hallo", greeting)
    }
}


