package ru.geekstar.ThreadJobs;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;

public abstract class Operations {

    protected Card fromCard;

    protected Card toCard;

    protected Account fromAccount;

    protected Account toAccount;

    protected float sum;


    public Operations(Card fromCard, Card toCard, float sum) {
        this(sum);
        this.fromCard = fromCard;
        this.toCard = toCard;
    }

    public Operations(Card fromCard, Account toAccount, float sum) {
        this(sum);
        this.fromCard = fromCard;
        this.toAccount = toAccount;
    }

    public Operations(Account fromAccount, Account toAccount, float sum) {
        this(sum);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public Operations(Account fromAccount, Card toCard, float sum) {
        this(sum);
        this.fromAccount = fromAccount;
        this.toCard = toCard;
    }

    public Operations(Card fromCard, float sum) {
        this(sum);
        this.fromCard = fromCard;
    }

    public Operations(float sum, Card toCard) {
        this(sum);
        this.toCard = toCard;
    }

    public Operations(float sum) {
        this.sum = sum;
    }

}
