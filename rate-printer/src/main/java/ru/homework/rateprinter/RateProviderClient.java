package ru.homework.rateprinter;

import org.springframework.web.client.RestTemplate;

public class RateProviderClient {

    private final RestTemplate restTemplate;
    private final String rpcUrl;

    public RateProviderClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.rpcUrl = baseUrl + "/rpc";
    }

    public RpcResponse getUsdRubRate() {
        RpcRequest request = new RpcRequest("getRate", "USDRUB");
        return restTemplate.postForObject(rpcUrl, request, RpcResponse.class);
    }
}
