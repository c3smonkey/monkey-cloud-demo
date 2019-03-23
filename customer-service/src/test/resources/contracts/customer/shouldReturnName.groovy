package contracts.customer

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/name'
        headers {
        }
    }
    response {
        status 200
        body("John")
        headers {
            contentType(textPlain())
        }
    }
}
