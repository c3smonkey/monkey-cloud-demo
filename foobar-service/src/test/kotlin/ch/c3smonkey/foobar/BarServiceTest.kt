package ch.c3smonkey.foobar

import org.junit.Assert.assertEquals
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
@AutoConfigureStubRunner(ids = ["ch.c3smonkey:customer-service:+:stubs:0"])
@DirtiesContext
class BarServiceTest {

    @Autowired
    internal var customerService: CustomerService? = null

    @Test
    @Throws(Exception::class)
    fun getNameTest() {
        val name = customerService!!.getName()
        assertEquals("John", name)
    }
}


