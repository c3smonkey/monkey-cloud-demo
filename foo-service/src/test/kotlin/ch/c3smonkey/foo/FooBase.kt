package ch.c3smonkey.foo

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before
import java.util.*

open class FooBase {

    @Before
    fun setup() {
        val alohaProperties = AlohaProperties()
        alohaProperties.greeting = "Hello"

        val greetings = HashMap<String, String>()
        greetings["EN"] = "Hello"
        greetings["ES"] = "Hola"
        greetings["DE"] = "Hallo"

        alohaProperties.greetings = greetings

        RestAssuredMockMvc.standaloneSetup(AlohaController(alohaProperties))
    }
}