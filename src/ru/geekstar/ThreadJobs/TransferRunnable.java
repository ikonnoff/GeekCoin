package ru.geekstar.ThreadJobs;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public class TransferRunnable extends Operations implements Runnable {

    public TransferRunnable(Card fromCard, Card toCard, float sum) {
        super(fromCard, toCard, sum);
    }

    public TransferRunnable(Card fromCard, Account toAccount, float sum) {
        super(fromCard, toAccount, sum);
    }

    public TransferRunnable(Account fromAccount, Account toAccount, float sum) {
        super(fromAccount, toAccount, sum);
    }

    public TransferRunnable(Account fromAccount, Card toCard, float sum) {
        super(fromAccount, toCard, sum);
    }


    @Override
    public void run() {
        if (fromCard != null) {
            if (toCard != null) fromCard.transferCard2Card(toCard, sum);
            if (toAccount != null) fromCard.transferCard2Account(toAccount, sum);
        }

        if (fromAccount != null) {
            if (toAccount != null) fromAccount.transferAccount2Account(toAccount, sum);
            if (toCard != null) fromAccount.transferAccount2Card(toCard, sum);
        }
    }

}
