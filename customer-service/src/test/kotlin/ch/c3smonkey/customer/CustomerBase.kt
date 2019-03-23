package ch.c3smonkey.customer

import io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@DirtiesContext
@AutoConfigureMessageVerifier
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["spring.cloud.config.enabled:false", "eureka.client.enabled:false"])
open abstract class CustomerBase {

    @get:Rule
    var restDocumentation = JUnitRestDocumentation()

    @get:Rule
    var testName = TestName()

    @Before
    fun setup() {
        val customerProperties = CustomerProperties()
        customerProperties.name = "John"
        customerProperties.names = mapOf("0" to "Baxter", "1" to "Dave")
        val customerService = CustomerService(customerProperties = customerProperties)


        standaloneSetup(MockMvcBuilders
                .standaloneSetup(CustomerController(customerService = customerService))
                .apply<StandaloneMockMvcBuilder>(
                        documentationConfiguration(this.restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint())
                )
                .alwaysDo(
                        document(
                                javaClass.simpleName + "_" + testName.getMethodName())
                )
        )
    }
}


