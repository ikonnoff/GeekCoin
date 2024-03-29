package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.TinkoffPayCardAccount;
import ru.geekstar.Bank.Tinkoff;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Transaction.PayMileTransaction;

import java.util.ArrayList;

public final class TinkoffAirlinesMir extends CardMir implements IAirlinesCard, IMulticurrencyCard {

    public static int countCards;

    private ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();


    @Override
    public ArrayList<PayCardAccount> getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;
    }


    public TinkoffAirlinesMir(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder,payCardAccount,pinCode);
        addAccount("EUR");
        countCards++;
    }

    @Override
    public int getMiles() {
        return ((TinkoffPhysicalPersonProfile) getCardHolder()).getMiles();
    }

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        super.payByCard(sumPay, buyProductOrService, pinCode);
        accumulateMiles(sumPay);
    }

    @Override
    public void accumulateMiles(float sumPay) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        int miles = 0;
        if (getPayCardAccount().getCurrencyCode().equals("RUB")) miles = (int)(sumPay/cardHolder.getCostOf1MileInRUB());
        if (getPayCardAccount().getCurrencyCode().equals("USD")) miles = (int)(sumPay/cardHolder.getCostOf1MileInUSD());
        if (getPayCardAccount().getCurrencyCode().equals("EUR")) miles = (int)(sumPay/cardHolder.getCostOf1MileInEUR());

        cardHolder.setMiles(cardHolder.getMiles() + miles);
    }

    @Override
    public void payByCardMiles(float sumPay, int milesPay, String buyProductOrService, String pinCode) {
        PayMileTransaction payMileTransaction = new PayMileTransaction(this, "Оплата милями", sumPay, milesPay, buyProductOrService);

        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        int balanceMiles = cardHolder.getMiles();
        if (balanceMiles >= milesPay) {
            if (milesPay > sumPay) milesPay = (int) sumPay;
            balanceMiles -= milesPay;
            cardHolder.setMiles(balanceMiles);
            sumPay -= milesPay;
            payMileTransaction.setStatusOperation("Оплата милями прошла успешно");
        } else payMileTransaction.setStatusOperation("Недостаточно миль");

        payMileTransaction.setPayMiles(milesPay);
        payMileTransaction.setBalanceMiles(balanceMiles);
        this.getPayCardAccount().getPayTransactions().add(payMileTransaction);

        payByCard(sumPay, buyProductOrService, pinCode);
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        PayCardAccount payCardAccount = (PayCardAccount) ((Tinkoff) this.getBank()).openAccount(this.getCardHolder(), TinkoffPayCardAccount.class, currencyCodeAccount);
        payCardAccount.getCards().add(this);
        getMulticurrencyAccounts().add(payCardAccount);
    }
}
