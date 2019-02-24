package ch.c3smonkey.inoa

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before

open class InoaBase {

    @Before
    fun setup() {
        val inoaProperties = InoaProperties()
        inoaProperties.inoa = "John"
        RestAssuredMockMvc.standaloneSetup(InoaController(inoaProperties))
    }
}


