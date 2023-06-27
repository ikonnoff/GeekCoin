package ru.geekstar.ThreadJobs;

import ru.geekstar.Card.Card;
import ru.geekstar.Card.IBonusCard;

public class PayByCardBonusRunnable extends PayByCardRunnable {

    private int bonusesPay;


    public PayByCardBonusRunnable(IBonusCard bonusCard, float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {
        super((Card) bonusCard, sumPay, buyProductOrService, pinCode);
        this.bonusesPay = bonusesPay;
    }


    @Override
    public void run() {
        IBonusCard bonusCard = (IBonusCard) fromCard;
        bonusCard.payByCardBonuses(sum, bonusesPay, buyProductOrService, pinCode);
    }

}
