package ru.geekstar.Card.IPaySystem;

public interface IPaySystem {

    // Конвертировать в валюту по курсу платёжной системы
    float convertToCurrencyExchangeRatePaySystem(float sum, String fromCurrencyCode, String toBillingCurrencyCode);

    // Запросить код валюты платёжной системы
    String getCurrencyCodePaySystem(String country);

    float getExchangeRatePaySystem(String currency, String currencyExchangeRate);

}
