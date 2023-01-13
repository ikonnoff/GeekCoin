package ru.geekstar.PhysicalPerson;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Account.SberSavingsAccount;
import ru.geekstar.Bank.IBankServicePhysicalPersons;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.Card.Card;
import ru.geekstar.Card.IBonusCard;
import ru.geekstar.Card.IMulticurrencyCard;
import ru.geekstar.Card.IPaySystem.IPaySystem;
import ru.geekstar.Card.SberMastercardTravel;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

public class PhysicalPerson {

    private String firstName;

    private String lastName;

    private String telephone;

    private byte age;

    private char gender;

    private PhysicalPersonProfile physicalPersonProfile;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public PhysicalPersonProfile getPhysicalPersonProfile() {
        return physicalPersonProfile;
    }

    public void setPhysicalPersonProfile(PhysicalPersonProfile physicalPersonProfile) {
        this.physicalPersonProfile = physicalPersonProfile;
    }

    public void registerPhysicalPersonToBank(IBankServicePhysicalPersons bank) {
        setPhysicalPersonProfile(bank.registerPhysicalPersonProfile(this));
    }

    public Card openCard(IBankServicePhysicalPersons bank, Card card, String currencyCode, String pinCode) {
        return bank.openCard(physicalPersonProfile, card, currencyCode, pinCode);
    }

    public Account openAccount(IBankServicePhysicalPersons bank, Account account, String currencyCode) {
        return bank.openAccount(physicalPersonProfile, account, currencyCode);
    }

    public void depositingCash2Card(Card toCard, float sumDepositing) {
        toCard.depositingCash2Card(sumDepositing);
    }

    public void payByCard(Card card, float sumPay, String buyProductOrService, String pinCode) {
        card.payByCard(sumPay, buyProductOrService, pinCode);
    }

    public void payByCard(Card card, float sumPay, String buyProductOrService, String country, String pinCode) {
        card.payByCard(sumPay, buyProductOrService, country, pinCode);
    }

    public void transferCard2Card(Card fromCard, Card toCard, float sumTransfer) {
        fromCard.transferCard2Card(toCard, sumTransfer);
    }

    public void transferCard2Account(Card fromCard, SberSavingsAccount toAccount, float sumTransfer) {
        fromCard.transferCard2Account(toAccount, sumTransfer);
    }

    public void transferAccount2Card(SberSavingsAccount fromAccount, Card toCard, float sumTransfer) {
        fromAccount.transferAccount2Card(toCard, sumTransfer);
    }

    public void transferAccount2Account(SberSavingsAccount fromAccount, SberSavingsAccount toAccount, float sumTransfer) {
        fromAccount.transferAccount2Account(toAccount, sumTransfer);
    }

    public void depositingCardFromCard(Card toCard, Card fromCard, float sumDepositing) {
        toCard.depositingCardFromCard(fromCard, sumDepositing);
    }

    public void depositingCardFromAccount(Card toCard, SberSavingsAccount fromAccount, float sumDepositing) {
        toCard.depositingCardFromAccount(fromAccount, sumDepositing);
    }

    public void depositingAccountFromCard(SberSavingsAccount toAccount, Card fromCard, float sumDepositing) {
        toAccount.depositingAccountFromCard(fromCard, sumDepositing);
    }

    public void depositingAccountFromAccount(SberSavingsAccount toAccount, SberSavingsAccount fromAccount, float sumDepositing) {
        toAccount.depositingAccountFromAccount(fromAccount, sumDepositing);
    }

    public float getExchangeRatePaySystem(IPaySystem paySystemCard, String currency, String currencyExchangeRate) {
        return paySystemCard.getExchangeRatePaySystem(currency, currencyExchangeRate);
    }

    public void displayCardTransactions(Card card) {
        card.displayCardTransactions();
    }

    public void displayAccountTransactions(SberSavingsAccount account) {
        account.displayAccountTransactions();
    }

    public void displayAccountTransactions(SberPayCardAccount account) {
        account.displayAccountTransactions();
    }

    public void displayProfileTransactions() {
        physicalPersonProfile.displayProfileTransactions();
    }

    public void addAccountToMulticurrencyCard(IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.addAccount(currencyCodeAccount);
    }

    public void switchAccountOfMulticurrencyCard(IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.switchAccount(currencyCodeAccount);
    }

    public void payByCardBonuses(IBonusCard bonusCard, float sumPay, int bonusesPay, String buyProductOrService) {
        bonusCard.payByCardBonuses(sumPay, bonusesPay, buyProductOrService);
    }
}