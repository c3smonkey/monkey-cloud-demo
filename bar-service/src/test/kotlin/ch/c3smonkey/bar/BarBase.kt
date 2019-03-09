package ch.c3smonkey.bar

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
open class BarBase {

    @get:Rule
    var restDocumentation = JUnitRestDocumentation()

    @get:Rule
    var testName = TestName()

    @Before
    fun setup() {

        val barProperties = BarProperties()
        barProperties.name = "John"


        RestAssuredMockMvc.standaloneSetup(BarController(barProperties))


        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .standaloneSetup(BarController(barProperties))
                .apply<StandaloneMockMvcBuilder>(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document(
                        javaClass.simpleName + "_" + testName.getMethodName())))
    }


}


