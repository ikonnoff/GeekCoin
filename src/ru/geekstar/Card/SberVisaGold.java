package ru.geekstar.Card;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;
import ru.geekstar.Transaction.PayBonusTransaction;

public final class SberVisaGold extends CardVisa implements IBonusCard {

    public static int countCards;

    public SberVisaGold(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder,payCardAccount,pinCode);
        countCards++;
    }

    @Override
    public int getBonuses() {
        return ((SberPhysicalPersonProfile) getCardHolder()).getBonuses();
    }

    @Override
    public void accumulateBonuses(float sumPay) {
        SberPhysicalPersonProfile cardHolder = (SberPhysicalPersonProfile) getCardHolder();
        int bonuses = Math.round((sumPay/100) * cardHolder.getPercentBonusOfSumPay());
        cardHolder.setBonuses(cardHolder.getBonuses() + bonuses);
    }

    @Override
    public void payByCardBonuses(float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {
        PayBonusTransaction payBonusTransaction = new PayBonusTransaction(this, "Оплата бонусами", sumPay,bonusesPay,buyProductOrService);

        SberPhysicalPersonProfile cardHolder = (SberPhysicalPersonProfile) getCardHolder();
        int balanceBonuses = cardHolder.getBonuses();
        if (balanceBonuses >= bonusesPay) {
            int sumPay99 = (int) ((sumPay / 100) * 99);
            if (bonusesPay > sumPay99) bonusesPay = sumPay99;
            balanceBonuses -= bonusesPay;
            cardHolder.setBonuses(balanceBonuses);
            sumPay -= bonusesPay;
            payBonusTransaction.setStatusOperation("Оплата бонусами прошла успешно");
        } else payBonusTransaction.setStatusOperation("Недостаточно бонусов");

        payBonusTransaction.setPayBonuses(bonusesPay);
        payBonusTransaction.setBalanceBonuses(balanceBonuses);
        this.getPayCardAccount().getPayTransactions().add(payBonusTransaction);

        payByCard(sumPay, buyProductOrService, pinCode);
    }

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        // вызовем родительскую версию метода
        super.payByCard(sumPay, buyProductOrService, pinCode);

        // и дополним метод уникальным поведением: начислим сбербонусы, которые присуще только картам Сбера
        accumulateBonuses(sumPay);

    }

    @Override
    public void transferCard2Card(Card toCard, float sumTransfer) {
        // вызовем родительскую версию метода
        super.transferCard2Card(toCard, sumTransfer);

        // и дополним метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимит
        ((SberPhysicalPersonProfile) getCardHolder()).updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toCard, sumTransfer);
    }

    @Override
    public void transferCard2Account(Account toAccount, float sumTransfer) {
        // вызовем родительскую версию метода
        super.transferCard2Account(toAccount, sumTransfer);

        // и дополним метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимит
        ((SberPhysicalPersonProfile) getCardHolder()).updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toAccount, sumTransfer);
    }
}
