package ru.geekstar.Account;

import ru.geekstar.Bank.Sberbank;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;
import ru.geekstar.Transaction.DepositingTransaction;
import ru.geekstar.Transaction.TransferTransaction;

public class Account {

    private Sberbank bank;

    private SberPhysicalPersonProfile accountHolder;

    private String numberAccount;

    private String currencyCode;

    private char currencySymbol;

    private float balance;

    private TransferTransaction[] transferTransactions = new TransferTransaction[50];

    private DepositingTransaction[] depositingTransactions = new DepositingTransaction[50];

    private int countTransferTransactions;

    private int countDepositingTransactions;


    public Sberbank getBank() {
        return bank;
    }

    public void setBank(Sberbank bank) {
        this.bank = bank;
    }

    public SberPhysicalPersonProfile getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(SberPhysicalPersonProfile accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public char getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencyCode) {
        if (currencyCode.equals("RUB")) this.currencySymbol = '₽';
        else if (currencyCode.equals("USD")) this.currencySymbol = '$';
        else if (currencyCode.equals("EUR")) this.currencySymbol = '€';
        else System.out.println("Недопустимый код валюты: " + currencyCode);
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        if (balance >= 0) this.balance = balance;
        else System.out.println("Отрицательное недопустимое значение баланса: " + balance);
    }

    public TransferTransaction[] getTransferTransactions() {
        return transferTransactions;
    }

    public void setTransferTransactions(TransferTransaction[] transferTransactions) {
        this.transferTransactions = transferTransactions;
    }

    public DepositingTransaction[] getDepositingTransactions() {
        return depositingTransactions;
    }

    public void setDepositingTransactions(DepositingTransaction[] depositingTransactions) {
        this.depositingTransactions = depositingTransactions;
    }

    public int getCountTransferTransactions() {
        return countTransferTransactions;
    }

    public void setCountTransferTransactions(int countTransferTransactions) {
        this.countTransferTransactions = countTransferTransactions;
    }

    public int getCountDepositingTransactions() {
        return countDepositingTransactions;
    }

    public void setCountDepositingTransactions(int countDepositingTransactions) {
        this.countDepositingTransactions = countDepositingTransactions;
    }

    // Пополнить баланс
    public boolean topUP(float sum) {
        balance += sum;
        return true;
    }

    // Добавить транзакцию о пополнении
    public void addDepositingTransaction(DepositingTransaction depositingTransaction) {
        depositingTransactions[countDepositingTransactions++] = depositingTransaction;
    }
}
