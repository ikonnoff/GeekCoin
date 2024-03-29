package ru.geekstar.ClientProfile;

import ru.geekstar.Bank.Bank;
import ru.geekstar.IOFile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public class TinkoffPhysicalPersonProfile extends PhysicalPersonProfile {

    private float cashback;

    private float percentCashbackOfSumPay;

    private int miles;

    private float costOf1MileInRUB;

    private float costOf1MileInUSD;

    private float costOf1MileInEUR;


    public float getCashback() {
        return cashback;
    }

    public void setCashback(float cashback) {
        this.cashback = cashback;
    }

    public float getPercentCashbackOfSumPay() {
        return percentCashbackOfSumPay;
    }

    public void setPercentCashbackOfSumPay(float percentCashbackOfSumPay) {
        this.percentCashbackOfSumPay = percentCashbackOfSumPay;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public float getCostOf1MileInRUB() {
        return costOf1MileInRUB;
    }

    public void setCostOf1MileInRUB(float costOf1MileInRUB) {
        this.costOf1MileInRUB = costOf1MileInRUB;
    }

    public float getCostOf1MileInUSD() {
        return costOf1MileInUSD;
    }

    public void setCostOf1MileInUSD(float costOf1MileInUSD) {
        this.costOf1MileInUSD = costOf1MileInUSD;
    }

    public float getCostOf1MileInEUR() {
        return costOf1MileInEUR;
    }

    public void setCostOf1MileInEUR(float costOf1MileInEUR) {
        this.costOf1MileInEUR = costOf1MileInEUR;
    }


    public TinkoffPhysicalPersonProfile(Bank bank, PhysicalPerson physicalPerson) {
        super(bank, physicalPerson);
    }

    @Override
    // Вывод всех операций по всем картам и счетам профиля физического лица в Тинькофф
    public String displayProfileTransactions() {
        // дополним метод уникальной информацией, присуще только Сберу
        String allTransactionsPhysicalPerson = "Все операции по картам и счетам клиента " + getPhysicalPerson().getFirstName() + " " + getPhysicalPerson().getLastName() +
                " в " + getBank().getBankName() + "Банке";

        String cashback = "Накоплено кэшбэка: " + getCashback();
        String balanceMiles = "Накоплено миль: " + getMiles();

        String headerProfileTransactions = allTransactionsPhysicalPerson + "\n" + cashback + "\n" + balanceMiles;

        String profileTransactions = headerProfileTransactions + "\n" + super.displayProfileTransactions();

        System.out.println(profileTransactions);
        IOFile.write(getPathToTransactionHistoryFile(), profileTransactions, true);

        return profileTransactions;
    }
}
