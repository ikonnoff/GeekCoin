package ru.geekstar.Account;

import ru.geekstar.Card.Card;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;

public class SberSavingsAccount extends SavingsAccount {

    public SberSavingsAccount(PhysicalPersonProfile accountHolder, String currencyCode) {
        super(accountHolder, currencyCode);
    }

    @Override
    public void transferAccount2Card(Card toCard, float sumTransfer) {
        // вызываем родительскую версию метода
        super.transferAccount2Card(toCard, sumTransfer);

        // и дополняем метод уникальным поведением
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц для контроля лимита
        ((SberPhysicalPersonProfile) getAccountHolder()).updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toCard, sumTransfer);
    }

    @Override
    public void transferAccount2Account(Account toAccount, float sumTransfer) {
        // вызываем родительскую версию метода
        super.transferAccount2Account(toAccount, sumTransfer);
        // и дополняем метод уникальным поведением
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц для контроля лимита
        ((SberPhysicalPersonProfile) getAccountHolder()).updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toAccount, sumTransfer);
    }

    @Override
    public String toString() {
        return "Сберегательный счёт ⦁⦁" + this.getNumberAccount().substring(20) + " " + getBalance() + " " + getCurrencySymbol();
    }

}
