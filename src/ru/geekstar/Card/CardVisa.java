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
        if (country.equalsIgnoreCase("Турция")) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_USD;

        return billingCurrencyCode;
    }

    // Запросить обменный курс валют платёжной системы
    public ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Visa
        ArrayList<Float> exchangeRatePaySystem = new ArrayList<>();
        // курс лиры к доллару
        if (currency.equals("TRY") && currencyExchangeRate.equals("USD")) {
            exchangeRatePaySystem.add(0.056f); // курс покупки
            exchangeRatePaySystem.add(0.046f); // курс продажи
        }
        return exchangeRatePaySystem;
    }

}
