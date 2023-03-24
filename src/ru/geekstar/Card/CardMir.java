package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Card.IPaySystem.IMir;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public abstract class CardMir extends Card implements IMir {

    public CardMir(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder,payCardAccount,pinCode);
    }

    @Override
    public String getCurrencyCodePaySystem(String country) {
        // по умолчанию null, потому что не во всех странах может использоваться данная платёжная система
        String billingCurrencyCode = null;
        // если покупка в Казахстане, то валюта биллинга в ₽
        if (country.equalsIgnoreCase("Казахстан")) billingCurrencyCode = CURRENCY_CODE_PAY_SYSTEM_RUB;

        return billingCurrencyCode;
    }

    @Override
    public ArrayList<Float> getExchangeRatePaySystem(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API Mir
        ArrayList<Float> exchangeRatePaySystem = new ArrayList<>();
        // курс тенге к рублю
        if (currency.equals("KZT") && currencyExchangeRate.equals("RUB")) {
            exchangeRatePaySystem.add(0.15f); // курс покупки
            exchangeRatePaySystem.add(0.13f); // курс продажи
        }
        return exchangeRatePaySystem;
    }
}
