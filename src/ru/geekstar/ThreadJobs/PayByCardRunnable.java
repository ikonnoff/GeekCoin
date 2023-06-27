package ru.geekstar.ThreadJobs;

import ru.geekstar.Card.Card;

public class PayByCardRunnable extends Operations implements Runnable {

    protected String buyProductOrService;

    protected String country;

    protected String pinCode;


    public PayByCardRunnable(Card fromCard, float sumPay, String buyProductOrService, String pinCode) {
        super(fromCard, sumPay);
        this.buyProductOrService = buyProductOrService;
        this.pinCode = pinCode;
    }

    public PayByCardRunnable(Card fromCard, float sumPay, String buyProductOrService, String country, String pinCode) {
        this(fromCard, sumPay, buyProductOrService, pinCode);
        this.country = country;
    }

    @Override
    public void run() {
        if (country == null) fromCard.payByCard(sum, buyProductOrService, pinCode);
        else fromCard.payByCard(sum, buyProductOrService, country, pinCode);
    }

}
