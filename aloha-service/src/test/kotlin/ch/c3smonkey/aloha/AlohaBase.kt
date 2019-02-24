package ch.c3smonkey.aloha

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before
import java.util.*

open class AlohaBase {

    @Before
    fun setup() {
        val alohaProperties = AlohaProperties()
        alohaProperties.aloha = "Hello"

        val greetings = HashMap<String, String>()
        greetings["EN"] = "Hello"
        greetings["ES"] = "Hola"
        greetings["DE"] = "Hallo"

        alohaProperties.alohas = greetings

        RestAssuredMockMvc.standaloneSetup(AlohaController(alohaProperties))
    }
}