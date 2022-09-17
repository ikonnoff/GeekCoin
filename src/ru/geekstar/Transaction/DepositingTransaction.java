package ru.geekstar.Transaction;

import ru.geekstar.Account.SberSavingsAccount;
import ru.geekstar.Card.SberVisaGold;

public class DepositingTransaction extends Transaction {

    private SberVisaGold toCard;

    private SberVisaGold fromCard;

    private SberSavingsAccount toAccount;

    private SberSavingsAccount fromAccount;


    public SberVisaGold getToCard() {
        return toCard;
    }

    public void setToCard(SberVisaGold toCard) {
        this.toCard = toCard;
    }

    public SberVisaGold getFromCard() {
        return fromCard;
    }

    public void setFromCard(SberVisaGold fromCard) {
        this.fromCard = fromCard;
    }

    public SberSavingsAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(SberSavingsAccount toAccount) {
        this.toAccount = toAccount;
    }

    public SberSavingsAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(SberSavingsAccount fromAccount) {
        this.fromAccount = fromAccount;
    }




}
