package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public class SberMastercardTravel extends CardMastercard implements IMulticurrencyCard {

    private ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();

    @Override
    public ArrayList<PayCardAccount> getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;
    }


    public SberMastercardTravel(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder,payCardAccount,pinCode);
        addAccount("USD");
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        // Открываем новый счёт
        PayCardAccount payCardAccount = (PayCardAccount) ((Sberbank) this.getBank()).openAccount(this.getCardHolder(), new SberPayCardAccount(), currencyCodeAccount);
        // Связываем созданный счёт с картой
        payCardAccount.getCards().add(this);
        // Добавляем созданный счёт в массив других счетов мультивалютной карты
        getMulticurrencyAccounts().add(payCardAccount);
    }

}
