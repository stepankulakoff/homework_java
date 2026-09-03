package ru.homework.currencyrateprovider;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateRpcController {

    private final CurrencyRateService currencyRateService;

    public RateRpcController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @PostMapping("/rpc")
    public RpcResponse handle(@RequestBody RpcRequest request) {
        if (!"getRate".equals(request.method())) {
            return RpcResponse.failure("unknown method: " + request.method());
        }
        return RpcResponse.success(currencyRateService.getUsdRubRate());
    }
}
