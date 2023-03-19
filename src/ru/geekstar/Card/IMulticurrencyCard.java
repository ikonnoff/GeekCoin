package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;

import java.util.ArrayList;

public interface IMulticurrencyCard {

    ArrayList<PayCardAccount> getMulticurrencyAccounts();

    void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts);

    PayCardAccount getPayCardAccount();

    void setPayCardAccount(PayCardAccount payCardAccount);


    void addAccount(String currencyCodeAccount);

    default void switchAccount(String currencyCodeAccount) {
        for (int idPayCardAccount = 0; idPayCardAccount < getMulticurrencyAccounts().size(); idPayCardAccount++) {
            PayCardAccount payCardAccount = getMulticurrencyAccounts().get(idPayCardAccount);
            String currencyCode = payCardAccount.getCurrencyCode();

            if (currencyCodeAccount.equals(currencyCode)) {
                getMulticurrencyAccounts().remove(payCardAccount);
                getMulticurrencyAccounts().add(getPayCardAccount());
                setPayCardAccount(payCardAccount);
            }
        }
    }

}
