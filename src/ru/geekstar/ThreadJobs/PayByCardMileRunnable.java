package ru.geekstar.ThreadJobs;

import ru.geekstar.Card.Card;
import ru.geekstar.Card.IAirlinesCard;

public class PayByCardMileRunnable extends PayByCardRunnable {

    private int milesPay;


    public PayByCardMileRunnable(IAirlinesCard airlinesCard, float sumPay, int milesPay, String buyProductOrService, String pinCode) {
        super((Card) airlinesCard, sumPay, buyProductOrService, pinCode);
        this.milesPay = milesPay;
    }


    @Override
    public void run() {
        IAirlinesCard airlinesCard = (IAirlinesCard) fromCard;
        airlinesCard.payByCardMiles(sum, milesPay, buyProductOrService, pinCode);
    }
}
