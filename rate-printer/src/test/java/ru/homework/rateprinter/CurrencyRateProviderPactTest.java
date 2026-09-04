package ru.homework.rateprinter;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "currency-rate-provider", pactVersion = PactSpecVersion.V3)
class CurrencyRateProviderPactTest {

    @Pact(consumer = "rate-printer", provider = "currency-rate-provider")
    RequestResponsePact getRatePact(PactDslWithProvider builder) {
        return builder
                .given("currency rate provider is available")
                .uponReceiving("a request for the USDRUB rate")
                .path("/rpc")
                .method("POST")
                .headers("Content-Type", "application/json")
                .body(new PactDslJsonBody()
                        .stringType("method", "getRate")
                        .stringType("pair", "USDRUB"))
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(new PactDslJsonBody()
                        .object("result", new PactDslJsonBody()
                                .stringType("pair", "USDRUB")
                                .numberType("rate"))
                        .nullValue("error"))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getRatePact")
    void getsUsdRubRateFromProvider(au.com.dius.pact.consumer.MockServer mockServer) {
        RateProviderClient client = new RateProviderClient(new RestTemplate(), mockServer.getUrl());

        RpcResponse response = client.getUsdRubRate();

        assertNotNull(response.result());
        assertEquals("USDRUB", response.result().pair());
    }
}
