package ru.homework.rateprinter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RatePrinterScheduler {

    private static final Logger log = LoggerFactory.getLogger(RatePrinterScheduler.class);

    private final RestTemplate restTemplate;
    private final String providerUrl;

    public RatePrinterScheduler(RestTemplate restTemplate,
                                 @Value("${currency-rate-provider.service-id}") String serviceId) {
        this.restTemplate = restTemplate;
        this.providerUrl = "http://" + serviceId + "/rpc";
    }

    @Scheduled(fixedRate = 5000)
    public void printRate() {
        RpcRequest request = new RpcRequest("getRate", "USDRUB");
        RpcResponse response = restTemplate.postForObject(providerUrl, request, RpcResponse.class);

        if (response == null || response.result() == null) {
            log.warn("Failed to get currency rate: {}", response == null ? "no response" : response.error());
            return;
        }

        CurrencyRate rate = response.result();
        log.info("{} = {}", rate.pair(), rate.rate());
    }
}
