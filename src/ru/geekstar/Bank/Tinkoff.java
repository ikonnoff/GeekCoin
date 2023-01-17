package ru.geekstar.Bank;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Account.TinkoffPayCardAccount;
import ru.geekstar.Card.Card;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public class Tinkoff extends Bank implements IBankServicePhysicalPersons {

    @Override
    public PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson) {
        // создать профиль клиента
        TinkoffPhysicalPersonProfile tinkoffPhysicalPersonProfile = new TinkoffPhysicalPersonProfile();
        tinkoffPhysicalPersonProfile.setBank(this);
        tinkoffPhysicalPersonProfile.setPhysicalPerson(physicalPerson);

        // установить лимиты
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(1000000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(50000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(3800.00f);

        tinkoffPhysicalPersonProfile.setPercentCashbackOfSumPay(1.0f);

        tinkoffPhysicalPersonProfile.setCostOf1MileInRUB(60.0f);
        tinkoffPhysicalPersonProfile.setCostOf1MileInUSD(1.0f);
        tinkoffPhysicalPersonProfile.setCostOf1MileInEUR(1.0f);

        // установить проценты комиссий
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(2.0f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(1.0f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(1.25f);

        // установить лимиты на суммы комиссий
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInRUB(3000.00f);
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(100.00f);

        // и привязать профиль клиента к банку
        getClientProfiles().add(tinkoffPhysicalPersonProfile);

        return tinkoffPhysicalPersonProfile;
    }

    @Override
    public float getCommissionOfTransferToClientBank(PhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode) {
        return 0;
    }

    @Override
    public float getExchangeRateBank(String currency, String currencyExchangeRate) {
        float exchangeRateBank = 0;
        // курс доллара к рублю
        if (currency.equals("USD") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 60.48f;
        // курс евро к рублю
        if (currency.equals("EUR") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 61.35f;
        return exchangeRateBank;
    }


}
