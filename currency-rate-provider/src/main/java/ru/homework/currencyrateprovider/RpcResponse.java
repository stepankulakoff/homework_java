package ru.homework.currencyrateprovider;

public record RpcResponse(CurrencyRate result, String error) {

    public static RpcResponse success(CurrencyRate rate) {
        return new RpcResponse(rate, null);
    }

    public static RpcResponse failure(String error) {
        return new RpcResponse(null, error);
    }
}
