package ru.geekstar.Account;

import ru.geekstar.Card.SberVisaGold;
import ru.geekstar.Transaction.PayTransaction;

public class PayCardAccount extends Account {

    private SberVisaGold[] cards = new SberVisaGold[2];

    private PayTransaction[] payTransactions = new PayTransaction[50];

    private float blockedSum;

    private byte countCards;

    private int countPayTransactions;


    public SberVisaGold[] getCards() {
        return cards;
    }

    public void setCards(SberVisaGold[] cards) {
        this.cards = cards;
    }

    public PayTransaction[] getPayTransactions() {
        return payTransactions;
    }

    public void setPayTransactions(PayTransaction[] payTransactions) {
        this.payTransactions = payTransactions;
    }

    public float getBlockedSum() {
        return blockedSum;
    }

    public void setBlockedSum(float blockedSum) {
        this.blockedSum = blockedSum;
    }

    public byte getCountCards() {
        return countCards;
    }

    public void setCountCards(byte countCards) {
        this.countCards = countCards;
    }

    public int getCountPayTransactions() {
        return countPayTransactions;
    }

    public void setCountPayTransactions(int countPayTransactions) {
        this.countPayTransactions = countPayTransactions;
    }

    // Привязать карту к платёжному счёту
    public void addCard(SberVisaGold card) {
        cards[countCards++] = card;
    }

    public boolean blockSum(float sum) {
        // сохранить сумму на счёте как зарезервированную
        blockedSum += sum;
        // и списать зарезервированную сумму со счёта, чтобы её нельзя было использовать одновременно при совершении других операций
        withdrawal(sum);
        return true;
    }

    // Списать зарезервированную сумму со счёта карты
    public boolean writeOffBlockedSum(float sum) {
        blockedSum -= sum;
        return true;
    }

    // Добавить транзакцию об оплате
    public void addPayTransaction(PayTransaction payTransaction) {
        payTransactions[countPayTransactions++] = payTransaction;
    }
}
