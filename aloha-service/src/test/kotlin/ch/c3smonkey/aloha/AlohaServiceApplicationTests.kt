package ch.c3smonkey.aloha

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["spring.cloud.config.enabled:false", "eureka.client.enabled:false"])
@TestPropertySource(locations = ["classpath:test.properties"])
class AlohaServiceApplicationTests {

    @Autowired
    internal var rest: TestRestTemplate? = null

    @Test
    fun contextLoads() {
        val deGreeting = this.rest!!.getForObject("/de", String::class.java)
        val enGreeting = this.rest!!.getForObject("/en", String::class.java)
        val esGreeting = this.rest!!.getForObject("/es", String::class.java)
        val defaultGreeting = this.rest!!.getForObject("/", String::class.java)
        assertEquals("Hallo", deGreeting)
        assertEquals("Hello", enGreeting)
        assertEquals("Hola", esGreeting)
        assertEquals("Hello", defaultGreeting)

    }

}
