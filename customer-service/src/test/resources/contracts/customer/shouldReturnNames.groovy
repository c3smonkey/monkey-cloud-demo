package contracts.customer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/names'
        headers {
        }
    }
    response {
        status 200
        body(
                0: "Baxter",
                1: "Dave"
        )
        headers {
            contentType(applicationJsonUtf8())
        }
    }
}
