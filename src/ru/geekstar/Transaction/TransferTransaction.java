package ru.geekstar.Transaction;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public class TransferTransaction extends Transaction {

    public TransferTransaction(Card fromCard, Card toCard, String typeOperation, float sum, String currencySymbol) {
        super(fromCard, toCard, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Card fromCard, Account toAccount, String typeOperation, float sum, String currencySymbol) {
        super(fromCard, toAccount, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Account fromAccount, Account toAccount, String typeOperation, float sum, String currencySymbol) {
        super(fromAccount, toAccount, typeOperation, sum, currencySymbol);
    }

    public TransferTransaction(Account fromAccount, Card toCard, String typeOperation, float sum, String currencySymbol) {
        super(fromAccount, toCard, typeOperation, sum, currencySymbol);
    }


    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getSender() + "\n" + getTypeOperation() + " " + getRecipient() + ": -" + getSum() + getCurrencySymbol() +
                "\nСтатус: " +  getStatusOperation() + "\nБаланс: " + getBalance() + getCurrencySymbol() + "\nКомиссия составила: " + getCommission() +
                getCurrencySymbol() + (getAuthorizationCode() != null ? " Код авторизации: " + getAuthorizationCode() : "");

        return transaction;

    }

}
