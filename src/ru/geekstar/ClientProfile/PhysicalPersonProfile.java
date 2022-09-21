package ru.geekstar.ClientProfile;

import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Account.SberSavingsAccount;
import ru.geekstar.Card.SberVisaGold;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public class PhysicalPersonProfile extends ClientProfile {

    private PhysicalPerson physicalPerson;

    private SberVisaGold[] cards = new SberVisaGold[5];

    private SberPayCardAccount[] payCardAccounts = new SberPayCardAccount[5];

    private SberSavingsAccount[] savingsAccounts = new SberSavingsAccount[15];

    private byte countCards;

    private byte countPayCardAccounts;

    private byte countSavingsAccounts;


    public PhysicalPerson getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPerson physicalPerson) {
        this.physicalPerson = physicalPerson;
    }

    public SberVisaGold[] getCards() {
        return cards;
    }

    public void setCards(SberVisaGold[] cards) {
        this.cards = cards;
    }

    public SberPayCardAccount[] getPayCardAccounts() {
        return payCardAccounts;
    }

    public void setPayCardAccounts(SberPayCardAccount[] payCardAccounts) {
        this.payCardAccounts = payCardAccounts;
    }

    public SberSavingsAccount[] getSavingsAccounts() {
        return savingsAccounts;
    }

    public void setSavingsAccounts(SberSavingsAccount[] savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public byte getCountCards() {
        return countCards;
    }

    public void setCountCards(byte countCards) {
        this.countCards = countCards;
    }

    public byte getCountPayCardAccounts() {
        return countPayCardAccounts;
    }

    public void setCountPayCardAccounts(byte countPayCardAccounts) {
        this.countPayCardAccounts = countPayCardAccounts;
    }

    public byte getCountSavingsAccounts() {
        return countSavingsAccounts;
    }

    public void setCountSavingsAccounts(byte countSavingsAccounts) {
        this.countSavingsAccounts = countSavingsAccounts;
    }


    // Привязать платёжный счёт к профилю клиента
    public void addAccount(SberPayCardAccount payCardAccount) {
        payCardAccounts[countPayCardAccounts++] = payCardAccount;
    }

    // Привязать сберегательный счёт к профилю клиента
    public void addAccount(SberSavingsAccount savingsAccount) {
        savingsAccounts[countSavingsAccounts++] = savingsAccount;
    }

    // Привязать карту к профилю клиента
    public void addCard(SberVisaGold card) {
        cards[countCards++] = card;
    }

    // проверить привязана ли карта к профилю клиента
    public boolean isClientCard(SberVisaGold card) {
        for (int idCard = 0; idCard < countCards; idCard++) {
            if (cards[idCard].equals(card)) return true;
        }
        return false;
    }

    // Прибавить сумму перевода на карту к общей сумме совершённых оплат и переводов в сутки, чтобы контролировать лимиты
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, SberVisaGold toCard) {
        // моя ли карта, на которую выполняем перевод
        boolean isMyCard = isClientCard(toCard);
        // если не моя карта, то обновляем общую сумму
        if (!isMyCard) updateTotalPaymentsTransfersDay(sum, fromCurrencyCode);
    }
}
