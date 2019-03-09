package ch.c3smonkey.bar

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before

class BarBase {

    @Before
    fun setup() {
        val barProperties = BarProperties()
        barProperties.name = "John"
        RestAssuredMockMvc.standaloneSetup(BarController(barProperties))
    }
}


