package ch.c3smonkey.foo

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before


open class FooBase {


    @Before
    fun setup() {
        val fooProperties = FooProperties()
        fooProperties.greeting = "Hello"

        val greetings = HashMap<String, String>()
        greetings["EN"] = "Hello"
        greetings["ES"] = "Hola"
        greetings["DE"] = "Hallo"

        fooProperties.greetings = greetings

        RestAssuredMockMvc.standaloneSetup(FooController(fooProperties))
    }
}