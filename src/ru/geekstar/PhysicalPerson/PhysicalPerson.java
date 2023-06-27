package ru.geekstar.PhysicalPerson;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Bank.Bank;
import ru.geekstar.Bank.IBankServicePhysicalPersons;
import ru.geekstar.Card.Card;
import ru.geekstar.Card.IAirlinesCard;
import ru.geekstar.Card.IBonusCard;
import ru.geekstar.Card.IMulticurrencyCard;
import ru.geekstar.Card.IPaySystem.IPaySystem;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ThreadJobs.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class PhysicalPerson {

    public static int personCount;

    private String firstName;

    private String lastName;

    private String telephone;

    private LocalDate dateOfBirth;

    private char gender;

    private ArrayList<PhysicalPersonProfile> physicalPersonProfiles = new ArrayList<>();


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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public ArrayList<PhysicalPersonProfile> getPhysicalPersonProfiles() {
        return physicalPersonProfiles;
    }

    public void setPhysicalPersonProfiles(ArrayList<PhysicalPersonProfile> physicalPersonProfiles) {
        this.physicalPersonProfiles = physicalPersonProfiles;
    }

    public PhysicalPerson(String firstName, String lastName, String telephone) {
        personCount++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
    }

    public PhysicalPerson(String firstName, String lastName, String telephone, LocalDate dateOfBirth, char gender) {
        this(firstName, lastName, telephone);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public PhysicalPersonProfile getPhysicalPersonProfile(Class<? extends Bank> classBank) {
        for (int idProfile = 0; idProfile < physicalPersonProfiles.size(); idProfile++) {
            PhysicalPersonProfile profile = physicalPersonProfiles.get(idProfile);
            // если объект банка является инстансом (экземпляром класса) classBank, то возвращаем найденный профиль
            if (classBank.isInstance(profile.getBank())) return profile;
        }
        return null;
    }

    public PhysicalPersonProfile getPhysicalPersonProfile(IBankServicePhysicalPersons bank) {
        for (int idProfile = 0; idProfile < physicalPersonProfiles.size(); idProfile++) {
            PhysicalPersonProfile profile = physicalPersonProfiles.get(idProfile);
            if (profile.getBank().equals(bank)) return profile;
        }
        return null;
    }

    public PhysicalPersonProfile registerPhysicalPersonToBank(IBankServicePhysicalPersons bank) {
        PhysicalPersonProfile physicalPersonProfile = bank.registerPhysicalPersonProfile(this);
        physicalPersonProfiles.add(physicalPersonProfile);
        return physicalPersonProfile;
    }

    public Card openCard(IBankServicePhysicalPersons bank, Class<? extends Card> classCard, Class<? extends PayCardAccount> classPayCardAccount, String currencyCode, String pinCode) {
        return bank.openCard(getPhysicalPersonProfile(bank), classCard, classPayCardAccount, currencyCode, pinCode);
    }

    public Account openAccount(IBankServicePhysicalPersons bank, Class<? extends Account> classAccount, String currencyCode) {
        return bank.openAccount(getPhysicalPersonProfile(bank), classAccount, currencyCode);
    }

    public void depositingCash2Card(Card toCard, float sumDepositing) {
        Runnable depositingJob = new DepositingRunnable(toCard, sumDepositing);
        threadStart(depositingJob);
    }

    public void payByCard(Card card, float sumPay, String buyProductOrService, String pinCode) {
        Runnable payByCardJob = new PayByCardRunnable(card, sumPay, buyProductOrService, pinCode);
        threadStart(payByCardJob);
    }

    public void payByCard(Card card, float sumPay, String buyProductOrService, String country, String pinCode) {
        Runnable payByCardJob = new PayByCardRunnable(card, sumPay, buyProductOrService, country, pinCode);
        threadStart(payByCardJob);
    }

    public void payByCardBonuses(IBonusCard bonusCard, float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {
        Runnable payByCardBonusJob = new PayByCardBonusRunnable(bonusCard, sumPay, bonusesPay, buyProductOrService, pinCode);
        threadStart(payByCardBonusJob);
    }

    public void payByCardMiles(IAirlinesCard airlinesCard, float sumPay, int milesPay, String buyProductOrService, String pinCode) {
        Runnable payByCardMileJob = new PayByCardMileRunnable(airlinesCard, sumPay, milesPay, buyProductOrService, pinCode);
        threadStart(payByCardMileJob);
    }

    public void transferCard2Card(Card fromCard, Card toCard, float sumTransfer) {
        Runnable transferJob = new TransferRunnable(fromCard, toCard, sumTransfer);
        threadStart(transferJob);
    }

    public void transferCard2Account(Card fromCard, Account toAccount, float sumTransfer) {
        Runnable transferJob = new TransferRunnable(fromCard, toAccount, sumTransfer);
        threadStart(transferJob);
    }

    public void transferAccount2Card(Account fromAccount, Card toCard, float sumTransfer) {
        Runnable transferJob = new TransferRunnable(fromAccount, toCard, sumTransfer);
        threadStart(transferJob);
    }

    public void transferAccount2Account(Account fromAccount, Account toAccount, float sumTransfer) {
        Runnable transferJob = new TransferRunnable(fromAccount, toAccount, sumTransfer);
        threadStart(transferJob);
    }

    public void depositingCardFromCard(Card toCard, Card fromCard, float sumDepositing) {
        Runnable depositingJob = new DepositingRunnable(fromCard, toCard, sumDepositing);
        threadStart(depositingJob);
    }

    public void depositingCardFromAccount(Card toCard, Account fromAccount, float sumDepositing) {
        Runnable depositingJob = new DepositingRunnable(fromAccount, toCard, sumDepositing);
        threadStart(depositingJob);
    }

    public void depositingAccountFromCard(Account toAccount, Card fromCard, float sumDepositing) {
        Runnable depositingJob = new DepositingRunnable(fromCard, toAccount, sumDepositing);
        threadStart(depositingJob);
    }

    public void depositingAccountFromAccount(Account toAccount, Account fromAccount, float sumDepositing) {
        Runnable depositingJob = new DepositingRunnable(fromAccount, toAccount, sumDepositing);
        threadStart(depositingJob);
    }

    public void threadStart(Runnable runnableJob) {
        Thread thread = new Thread(runnableJob);
        thread.start();
    }

    public ArrayList<Float> getExchangeRatePaySystem(IPaySystem paySystemCard, String currency, String currencyExchangeRate) {
        return paySystemCard.getExchangeRatePaySystem(currency, currencyExchangeRate);
    }

    public void displayCardTransactions(Card card) {
        card.displayCardTransactions();
    }

    public void displayAccountTransactions(Account account) {
        account.displayAccountTransactions();
    }

    public void displayProfileTransactions(IBankServicePhysicalPersons bank) {
        getPhysicalPersonProfile(bank).displayProfileTransactions();
    }

    public void displayAllProfileTransactions() {
        for (int idProfile = 0; idProfile < physicalPersonProfiles.size(); idProfile++) {
            physicalPersonProfiles.get(idProfile).displayProfileTransactions();
        }
    }

    public void displayTransactionHistory() {
        for (int idProfile = 0; idProfile < physicalPersonProfiles.size(); idProfile++) {
            physicalPersonProfiles.get(idProfile).displayTransactionHistory();
        }
    }

    public void clearTransactionHistory() {
        for (int idProfile = 0; idProfile < physicalPersonProfiles.size(); idProfile++) {
            physicalPersonProfiles.get(idProfile).clearTransactionHistory();
        }
    }

    public void addAccountToMulticurrencyCard(IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.addAccount(currencyCodeAccount);
    }

    public void switchAccountOfMulticurrencyCard(IMulticurrencyCard multicurrencyCard, String currencyCodeAccount) {
        multicurrencyCard.switchAccount(currencyCodeAccount);
    }

    public void displayMulticurrencyCardTransactions(IMulticurrencyCard multicurrencyCard) {
        multicurrencyCard.displayMulticurrencyCardTransactions();
    }

    public String toString() {
        return lastName + " " + firstName;
    }

}