package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Bank.IBankServicePhysicalPersons;
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

    @Override
    public void addAccount(IBankServicePhysicalPersons bank, PhysicalPersonProfile physicalPersonProfile, String currencyCodeAccount) {
        // Открываем новый счёт
        SberPayCardAccount sberPayCardAccount = (SberPayCardAccount) bank.openAccount(physicalPersonProfile, new SberPayCardAccount(), currencyCodeAccount);
        // Связываем созданный счёт с картой
        sberPayCardAccount.addCard(this);
        // Добавляем созданный счёт в массив других счетов мультивалютной карты
        getMulticurrencyAccounts().add(sberPayCardAccount);
    }

    @Override
    public void switchAccount(String currencyCodeAccount) {
        // попробуйте реализовать и прислать мне свой вариант
        // если покажется сложным, то я дам псевдокод в качестве подсказки
    }
}
