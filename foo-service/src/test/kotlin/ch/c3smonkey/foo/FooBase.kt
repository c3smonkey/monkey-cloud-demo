package ch.c3smonkey.foo

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@DirtiesContext
@AutoConfigureMessageVerifier
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["spring.cloud.config.enabled:false", "eureka.client.enabled:false"])
class FooBase {

    @get:Rule
    var restDocumentation = JUnitRestDocumentation()

    @get:Rule
    var testName = TestName()

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

        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .standaloneSetup(FooController(fooProperties))
                .apply<StandaloneMockMvcBuilder>(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document(
                        javaClass.simpleName + "_" + testName.getMethodName())))
    }
}
