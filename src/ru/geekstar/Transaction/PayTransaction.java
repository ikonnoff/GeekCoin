package ru.geekstar.Transaction;

import ru.geekstar.Card.Card;

public class PayTransaction extends Transaction {

    private String buyProductOrService;


    public String getBuyProductOrService() {
        return buyProductOrService;
    }

    public void setBuyProductOrService(String buyProductOrService) {
        this.buyProductOrService = buyProductOrService;
    }


    public PayTransaction(Card fromCard, String typeOperation, float sumPay, String buyProductOrService) {
        super(fromCard, typeOperation, sumPay);
        this.buyProductOrService = buyProductOrService;
    }

    @Override
    public String getStringTransaction() {
        String consumer = getNameCard(getFromCard());

        String transaction = getLocalDateTime() + " " + consumer + "\n" + getTypeOperation() + " " + buyProductOrService + ": -" + getSum() + " " + getCurrencySymbol() +
                "\nСтатус: " + getStatusOperation() + "\nБаланс: " + getBalance() + " " + getCurrencySymbol() + "\nКомиссия составила: " + getCommission() +
                " " + getCurrencySymbol() + " Код авторизации: " + getAuthorizationCode();

        return transaction;

    }


}
