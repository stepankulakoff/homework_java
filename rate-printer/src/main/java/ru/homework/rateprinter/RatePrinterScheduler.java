package ru.homework.rateprinter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RatePrinterScheduler {

    private static final Logger log = LoggerFactory.getLogger(RatePrinterScheduler.class);

    private final RateProviderClient rateProviderClient;

    public RatePrinterScheduler(RateProviderClient rateProviderClient) {
        this.rateProviderClient = rateProviderClient;
    }

    @Scheduled(fixedRate = 5000)
    public void printRate() {
        RpcResponse response = rateProviderClient.getUsdRubRate();

        if (response == null || response.result() == null) {
            log.warn("Failed to get currency rate: {}", response == null ? "no response" : response.error());
            return;
        }

        CurrencyRate rate = response.result();
        log.info("{} = {}", rate.pair(), rate.rate());
    }
}
