package ru.homework.rateprinter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class RatePrinterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatePrinterApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RateProviderClient rateProviderClient(RestTemplate restTemplate,
                                                   @Value("${currency-rate-provider.service-id}") String serviceId) {
        return new RateProviderClient(restTemplate, "http://" + serviceId);
    }
}
