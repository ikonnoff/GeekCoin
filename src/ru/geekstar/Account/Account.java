package ru.geekstar.Account;

import ru.geekstar.Bank.Sberbank;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;
import ru.geekstar.Transaction.DepositingTransaction;
import ru.geekstar.Transaction.TransferTransaction;

import java.util.Arrays;

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

    public boolean setBalance(float balance) {
        if (balance >= 0) {
            this.balance = balance;
            return true;
        }
        else {
            System.out.println("Отрицательное недопустимое значение баланса: " + balance);
            return false;
        }
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
        setBalance(balance + sum);
        return true;
    }

    // Добавить транзакцию о пополнении
    public void addDepositingTransaction(DepositingTransaction depositingTransaction) {
        depositingTransactions[countDepositingTransactions++] = depositingTransaction;
    }

    // Проверить достаточно ли денег на балансе
    public boolean checkBalance(float sum) {
        if (sum <= balance) return true;
        return false;
    }

    // Списать средства в три попытки в случае сбоя
    public boolean withdrawal(float sum) {
        boolean writeOffStatus;
        byte errorTransaction = 0;
        do {
            writeOffStatus = writeOff(sum);
            if (!writeOffStatus) errorTransaction++;
        } while (!writeOffStatus && errorTransaction < 3);

        return writeOffStatus;
    }

    // Списать со счёта
    private boolean writeOff(float sum) {
        return setBalance(balance - sum);
    }

    // Вывести транзакции по счёту
    public void displayAccountTransactions() {
        // сформировать общий массив транзакций перевода и пополнения в человекочитаемом формате
        String[] allTransferDepositingTransactions = getAllTransferDepositingTransactions();

        // отсортировать транзакции по дате
        Arrays.sort(allTransferDepositingTransactions);

        // вывести все транзакции
        for (int idTransaction = 0; idTransaction < allTransferDepositingTransactions.length; idTransaction++) {
            System.out.println("#" + idTransaction + " " + allTransferDepositingTransactions[idTransaction]);
        }

    }

    public String[] getAllTransferDepositingTransactions() {
        // объявить массив транзакций перевода и пополнения по счёту длиной равной общему количеству транзакций
        String[] allTransferDepositingTransactions = new String[countTransferTransactions + countDepositingTransactions];

        int countTransferDepositingTransactions = 0;
        // перебрать транзакции перевода и пополнения и добавить их в общий массив в человекочитаемом формате
        for (int idTransaction = 0; idTransaction < countTransferTransactions; idTransaction++) {
            allTransferDepositingTransactions[countTransferDepositingTransactions++] = transferTransactions[idTransaction].getStringTransaction();
        }
        for (int idTransaction = 0; idTransaction < countDepositingTransactions; idTransaction++) {
            allTransferDepositingTransactions[countTransferDepositingTransactions++] = depositingTransactions[idTransaction].getStringTransaction();
        }

        return allTransferDepositingTransactions;
    }
}
