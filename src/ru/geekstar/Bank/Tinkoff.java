package ru.geekstar.Bank;

import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public class Tinkoff extends Bank implements IBankServicePhysicalPersons {

    public static final String TINKOFF;

    static {
        TINKOFF = "Тинькофф";
        System.out.println(TINKOFF + " для физических лиц");
    }

    public Tinkoff() {
        this(TINKOFF);
    }

    public Tinkoff(String bankName) {
        super(bankName);
    }

    @Override
    public PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson) {
        // создать профиль клиента
        TinkoffPhysicalPersonProfile tinkoffPhysicalPersonProfile = new TinkoffPhysicalPersonProfile(this, physicalPerson);

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
    public ArrayList<Float> getExchangeRateBank(String currency, String currencyExchangeRate) {
        ArrayList<Float> exchangeRateBank = new ArrayList<>();
        // курс доллара к рублю
        if (currency.equals("USD") && currencyExchangeRate.equals("RUB")) {
            exchangeRateBank.add(63.18f); // курс покупки
            exchangeRateBank.add(60.48f); // курс продажи
        }
        // курс евро к рублю
        if (currency.equals("EUR") && currencyExchangeRate.equals("RUB")) {
            exchangeRateBank.add(64.15f); // курс покупки
            exchangeRateBank.add(61.35f); // курс продажи
        }
        return exchangeRateBank;
    }


}
