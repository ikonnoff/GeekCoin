package ru.geekstar.Transaction;

import ru.geekstar.Card.Card;

public class PayMileTransaction extends PayTransaction {

    private int payMiles;

    private int balanceMiles;


    public int getPayMiles() {
        return payMiles;
    }

    public void setPayMiles(int payMiles) {
        this.payMiles = payMiles;
    }

    public int getBalanceMiles() {
        return balanceMiles;
    }

    public void setBalanceMiles(int balanceMiles) {
        this.balanceMiles = balanceMiles;
    }


    public PayMileTransaction(Card fromCard, String typeOperation, float sumPay, int payMiles, String buyProductOrService) {
        super(fromCard, typeOperation, sumPay,buyProductOrService);
        this.payMiles = payMiles;
    }


    @Override
    public String getStringTransaction() {
        String consumer = getNameCard(getFromCard());

        String transaction = getLocalDateTime() + " " + consumer + "\n" + getTypeOperation() + " " + getBuyProductOrService() + ": -" + payMiles +
                " миль" + "\nСтатус: " + getStatusOperation() + "\nДоступно миль: " + balanceMiles;

        return transaction;

    }

}
