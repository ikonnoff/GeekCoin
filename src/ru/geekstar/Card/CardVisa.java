package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Card.IPaySystem.IVisa;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public abstract class CardVisa extends Card implements IVisa {

    public CardVisa(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder,payCardAccount,pinCode);
    }

    @Override
    // Запросить код валюты платёжной системы
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платёжная система
        String billingCurrencyCode = null;
        // если покупка в Турции, то валюта биллинга в $
        if (country.equalsIgnoreCase("Турция") || country.equalsIgnoreCase("Казахстан")
                || country.equalsIgnoreCase("Франция")) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_USD;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платёжной системы
    public ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Visa
        ArrayList<Float> exchangeRatePaySystem = new ArrayList<>();

        // курс лиры в долларах
        if (currency.equals("TRY") && currencyExchangeRate.equals("USD")) {
            exchangeRatePaySystem.add(0.0448f); // курс покупки
            exchangeRatePaySystem.add(0.0496f); // курс продажи
        }

        // курс тенге в долларах
        if (currency.equals("KZT") && currencyExchangeRate.equals("USD")) {
            exchangeRatePaySystem.add(0.0021f);
            exchangeRatePaySystem.add(0.0023f);
        }

        // курс евро в долларах
        if (currency.equals("EUR") && currencyExchangeRate.equals("USD")) {
            exchangeRatePaySystem.add(1.024f);
            exchangeRatePaySystem.add(1.132f);
        }

        return exchangeRatePaySystem;
    }

}
