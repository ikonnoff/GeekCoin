package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Transaction.DepositingTransaction;

import java.time.LocalDateTime;

public final class TinkoffBlackMir extends CardMir implements ICashbackCard {

    public TinkoffBlackMir(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder,payCardAccount,pinCode);
    }

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        super.payByCard(sumPay, buyProductOrService, pinCode);
        accumulateCashback(sumPay);
    }

    @Override
    public void accumulateCashback(float sumPay) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        float cashback = ((sumPay / 100.0f) * cardHolder.getPercentCashbackOfSumPay());
        cardHolder.setCashback(cardHolder.getCashback() + cashback);
    }

    @Override
    public void depositingCashback2Card() {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        // инициализировать транзакцию пополнения
        DepositingTransaction depositingTransaction = new DepositingTransaction();
        depositingTransaction.setLocalDateTime(LocalDateTime.now());
        depositingTransaction.setToCard(this);
        depositingTransaction.setSum(cardHolder.getCashback());
        depositingTransaction.setCurrencySymbol(getPayCardAccount().getCurrencySymbol());
        depositingTransaction.setTypeOperation("Зачисление кэшбэка");

        // выполняем пополнение
        boolean topUpStatus = getPayCardAccount().topUp(cardHolder.getCashback());

        // внести в транзакцию статус пополнения
        if (topUpStatus) depositingTransaction.setStatusOperation("Зачисление кэшбэка прошло успешно");
        else depositingTransaction.setStatusOperation("Зачисление кэшбэка не прошло");

        // внести в транзакцию баланс карты после пополнения
        depositingTransaction.setBalance(getPayCardAccount().getBalance());

        // добавить и привязать транзакцию пополнения к счёту карты зачисления
        getPayCardAccount().getDepositingTransactions().add(depositingTransaction);
    }

}
