package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.TinkoffPayCardAccount;
import ru.geekstar.Bank.Tinkoff;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Transaction.PayMileTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TinkoffAirlinesMir extends CardMir implements IAirlinesCard, IMulticurrencyCard {

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
        PayMileTransaction payMileTransaction = new PayMileTransaction();
        payMileTransaction.setLocalDateTime(LocalDateTime.now());
        payMileTransaction.setFromCard(this);
        payMileTransaction.setTypeOperation("Оплата милями");
        payMileTransaction.setBuyProductOrService(buyProductOrService);

        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();

        if (cardHolder.getMiles() >= milesPay) {
            if (milesPay > sumPay) milesPay = (int) sumPay;
            cardHolder.setMiles(cardHolder.getMiles() - milesPay);
            sumPay -= milesPay;
            payMileTransaction.setStatusOperation("Оплата милями прошла успешно");
        } else payMileTransaction.setStatusOperation("Недостаточно миль");

        payMileTransaction.setPayMiles(milesPay);
        payMileTransaction.setBalanceMiles(cardHolder.getMiles());
        this.getPayCardAccount().getPayTransactions().add(payMileTransaction);

        payByCard(sumPay, buyProductOrService, pinCode);
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        PayCardAccount payCardAccount = (PayCardAccount) ((Tinkoff) this.getBank()).openAccount(this.getCardHolder(), new TinkoffPayCardAccount(), currencyCodeAccount);
        payCardAccount.getCards().add(this);
        getMulticurrencyAccounts().add(payCardAccount);
    }
}
