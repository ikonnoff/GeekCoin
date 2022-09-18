package ru.geekstar.Transaction;

import ru.geekstar.Card.SberVisaGold;

public class PayTransaction extends Transaction {

    private SberVisaGold fromCard;

    private String buyProductOrService;


    public SberVisaGold getFromCard() {
        return fromCard;
    }

    public void setFromCard(SberVisaGold fromCard) {
        this.fromCard = fromCard;
    }

    public String getBuyProductOrService() {
        return buyProductOrService;
    }

    public void setBuyProductOrService(String buyProductOrService) {
        this.buyProductOrService = buyProductOrService;
    }
}
