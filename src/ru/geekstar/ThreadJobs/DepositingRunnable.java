package ru.geekstar.ThreadJobs;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public class DepositingRunnable extends Operations implements Runnable {

    public DepositingRunnable(Card fromCard, Card toCard, float sum) {
        super(fromCard, toCard, sum);
    }

    public DepositingRunnable(Card fromCard, Account toAccount, float sum) {
        super(fromCard, toAccount, sum);
    }

    public DepositingRunnable(Account fromAccount, Account toAccount, float sum) {
        super(fromAccount, toAccount, sum);
    }

    public DepositingRunnable(Account fromAccount, Card toCard, float sum) {
        super(fromAccount, toCard, sum);
    }

    public DepositingRunnable(Card toCard, float sum) {
        super(sum, toCard);
    }


    @Override
    public void run() {
        if (toCard != null) {
            if (fromCard != null) toCard.depositingCardFromCard(fromCard, sum);
            else if (fromAccount != null) toCard.depositingCardFromAccount(fromAccount, sum);
            else toCard.depositingCash2Card(sum);
        }

        if (toAccount != null) {
            if (fromAccount != null) toAccount.depositingAccountFromAccount(fromAccount, sum);
            if (fromCard != null) toAccount.depositingAccountFromCard(fromCard, sum);
        }
    }

}
