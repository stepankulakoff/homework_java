package ru.homework.currencyrateprovider;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CurrencyRateService {

    private static final double BASE_USD_RUB = 90.0;
    private static final double MAX_DEVIATION = 2.0;

    public CurrencyRate getUsdRubRate() {
        double deviation = ThreadLocalRandom.current().nextDouble(-MAX_DEVIATION, MAX_DEVIATION);
        double rate = Math.round((BASE_USD_RUB + deviation) * 100.0) / 100.0;
        return new CurrencyRate("USDRUB", rate);
    }
}
