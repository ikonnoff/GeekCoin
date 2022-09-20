package ru.geekstar.Card;

import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Account.SberSavingsAccount;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;
import ru.geekstar.Transaction.DepositingTransaction;
import ru.geekstar.Transaction.PayTransaction;

import java.time.LocalDateTime;

public class Card {

    private Sberbank bank;

    private SberPhysicalPersonProfile cardHolder;

    private SberPayCardAccount payCardAccount;

    private String numberCard;

    private String statusCard;


    public Sberbank getBank() {
        return bank;
    }

    public void setBank(Sberbank bank) {
        this.bank = bank;
    }

    public SberPhysicalPersonProfile getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(SberPhysicalPersonProfile cardHolder) {
        this.cardHolder = cardHolder;
    }

    public SberPayCardAccount getPayCardAccount() {
        return payCardAccount;
    }

    public void setPayCardAccount(SberPayCardAccount payCardAccount) {
        this.payCardAccount = payCardAccount;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        String number = numberCard.replace(" ", "");
        String regex = "[0-9]+";
        if (number.length() == 16 && number.matches(regex)) this.numberCard = numberCard;
        else System.out.println("Недопустимый номер карты");
    }

    public String getStatusCard() {
        return statusCard;
    }

    public void setStatusCard(String statusCard) {
        this.statusCard = statusCard;
    }

    // Оплатить картой
    public void payByCard(float sumPay, String buyProductOrService) {
        // инициализировать транзакцию оплаты
        PayTransaction payTransaction = new PayTransaction();
        payTransaction.setLocalDateTime(LocalDateTime.now());
        payTransaction.setFromCard((SberVisaGold) this);
        payTransaction.setSum(sumPay);
        payTransaction.setCurrencySymbol(payCardAccount.getCurrencySymbol());
        payTransaction.setTypeOperation("Покупка");
        payTransaction.setBuyProductOrService(buyProductOrService);

        // рассчитать комиссию при оплате
        float commission = bank.getCommission(cardHolder, sumPay, buyProductOrService);

        // внести в транзакцию данные о комиссии
        payTransaction.setCommission(commission);

        // запросить разрешение банка на проведение операции с блокированием суммы оплаты и комиссии
        String authorization = bank.authorization((SberVisaGold) this, payTransaction.getTypeOperation(), sumPay, commission);
        // извлекаем массив строк разделяя их символом @
        String[] authorizationData = authorization.split("@");
        // извлекаем код авторизации
        String authorizationCode = authorizationData[0];
        payTransaction.setAuthorizationCode(authorizationCode);
        // извлекаем сообщение авторизации
        String authorizationMessage = authorizationData[1];  // "Success: Авторизация прошла успешно"
        // извлекаем статус из сообщения авторизации
        String authorizationStatus = authorizationMessage.substring(0, authorizationMessage.indexOf(":"));
        // если разрешение получено, то выполняем списание зарезервированной суммы и комиссии со счёта карты
        if (authorizationStatus.equalsIgnoreCase("Success")) {
            boolean writeOffBlockedSum = payCardAccount.writeOffBlockedSum(sumPay + commission);
            if (writeOffBlockedSum) {
                // внести в транзакцию статус оплаты
                payTransaction.setStatusOperation("Оплата прошла успешно");

                // TODO: перевести сумму на счёт магазина, а комиссию на счёт банка

                // прибавить сумму оплаты к общей сумме совершенных оплат и переводов за сутки, чтобы контролировать лимиты
                getCardHolder().updateTotalPaymentsTransfersDay(sumPay, payCardAccount.getCurrencyCode());
            } else payTransaction.setStatusOperation("Оплата не прошла");
        } else {
            // иначе выводим сообщение о статусе авторизации, чтобы понимать что пошло не так
            String authorizationStatusMessage = authorizationMessage.substring(authorizationMessage.indexOf(":") + 2);
            payTransaction.setStatusOperation(authorizationStatusMessage);
        }

        // внести в транзакцию баланс счёта картв после оплаты
        payTransaction.setBalance(getPayCardAccount().getBalance());

        // добавить и привязать транзакцию оплаты к счёту карты
        payCardAccount.addPayTransaction(payTransaction);
    }

    // Оплатить картой за рубежом
    public void payByCard(float sumPay, String buyProductOrService, String country) {

    }

    // Перевести с карты на карту
    public void transferCard2Card(SberVisaGold toCard, float sumTransfer) {

    }

    // Перевести с карты на счёт
    public void transferCard2Account(SberSavingsAccount toAccount, float sumTransfer) {

    }

    // Внести наличные на карту
    public void depositingCash2Card(float sumDepositing) {
        // инициализировать транзакцию пополнения
        DepositingTransaction depositingTransaction = new DepositingTransaction();
        depositingTransaction.setLocalDateTime(LocalDateTime.now());
        depositingTransaction.setToCard((SberVisaGold) this);
        depositingTransaction.setSum(sumDepositing);
        depositingTransaction.setCurrencySymbol(payCardAccount.getCurrencySymbol());
        depositingTransaction.setTypeOperation("Внесение наличных");

        // запросить разрешение банка на проведение операции с проверкой статуса карты
        String authorization = bank.authorization((SberVisaGold) this, depositingTransaction.getTypeOperation(), sumDepositing, 0);
        // извлекаем массив строк разделяя их символом @
        String[] authorizationData = authorization.split("@");
        // извлекаем код авторизации
        String authorizationCode = authorizationData[0];
        // вносим в транзакцию код авторизации
        depositingTransaction.setAuthorizationCode(authorizationCode);
        // извлекаем сообщение из авторизации
        String authorizationMessage = authorizationData[1];
        // извлекаем статус из сообщения авторизации
        String authorizationStatus = authorizationMessage.substring(0, authorizationMessage.indexOf(":"));

        // если разрешение получено, то выполняем пополнение
        if (authorizationStatus.equalsIgnoreCase("Success")) {
            boolean topUpStatus = payCardAccount.topUP(sumDepositing);
            if (topUpStatus) {
                // внести в транзакцию статус пополнения
                depositingTransaction.setStatusOperation("Внесение наличных прошло успешно");
            } else depositingTransaction.setStatusOperation("Внесение наличных не прошло");
        } else {
            // иначе выводим сообщение о статусе авторизации, чтобы понимать что пошло не так
            String authorizationStatusMessage = authorizationMessage.substring(authorizationMessage.indexOf(":"));
            depositingTransaction.setStatusOperation(authorizationStatusMessage);
        }

        // внести в транзакцию баланс карты после пополнения
        depositingTransaction.setBalance(getPayCardAccount().getBalance());

        // TODO: добавить и привязать транзакцию пополнения к счёту карты зачисления
        payCardAccount.addDepositingTransaction(depositingTransaction);
    }

    // Пополнить карту с карты
    public void depositingCardFromCard(SberVisaGold fromCard, float sumDepositing) {

    }

    // Пополнить карту со счёта
    public void depositingCardFromAccount(SberSavingsAccount fromAccount, float sumDepositing) {

    }

    // Вывести транзакции по счёту карты
    public void displayCardTransactions() {
        payCardAccount.displayAccountTransactions();
    }


    /*
    public void pay(float sumPay) {
        // списать сумму покупки с карты
        boolean payStatus;
        byte errorTransaction = 0;
        do {
            payStatus = withdrawal(sumPay);
            if (payStatus) { // payStatus == true
                String transaction = paySystem + " " + numberCard + ": " + "Покупка " + sumPay + currency + " Остаток на карте " + deposit + currency;
                setTransactions(transaction);
                //System.out.println(transaction);
            } else errorTransaction++;
        } while (!payStatus && errorTransaction < 3);

         TODO: перевести сумму на счет магазина

    }

    public void transfer(float sumTransfer) {
        // нужно рассчитать комиссию за перевод
        float comission;
        if (sumTransfer < 50000) {
            comission = 0.0f;
        } else { // sumTransfer == 50000 || sumTransfer > 50000
            comission = sumTransfer * 0.01f;
        }

        // затем списать деньги с карты
        boolean transferStatus;
        byte errorTransaction = 0;
        do {
            transferStatus = withdrawal(sumTransfer + comission);
            if (transferStatus) {
                String transaction = paySystem + " " + numberCard + ": " + "Переведено " + sumTransfer + currency + " Комиссия составила " + comission + currency + " Остаток на карте " + deposit + currency;
                setTransactions(transaction);
            } else errorTransaction++;
        } while (!transferStatus && errorTransaction < 3);

        // перевести деньги на другую карту

        // и перевести комиссию на счёт банка

    }

    private void depositing(float sumDepositing) {
        // внесение денег на карту
        deposit = deposit + sumDepositing;
        String transaction = paySystem + " " + numberCard + ": " + "Внесено " + sumDepositing + currency + " Остаток на карте " + deposit + currency;
        setTransactions(transaction);
    }

    private boolean withdrawal(float sum) {
        if (deposit >= sum) {
            deposit = deposit - sum;
            return true;
        } else {
            String transaction = paySystem + " " + numberCard + ": " + "Недостаточно средств на карте " + deposit + currency;
            setTransactions(transaction);
            return false;
        }
    }
    */

}
