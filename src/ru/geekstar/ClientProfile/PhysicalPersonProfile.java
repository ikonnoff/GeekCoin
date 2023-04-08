package ru.geekstar.ClientProfile;

import ru.geekstar.Account.Account;
import ru.geekstar.Bank.Bank;
import ru.geekstar.Card.Card;
import ru.geekstar.IOFile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class PhysicalPersonProfile extends ClientProfile {

    private PhysicalPerson physicalPerson;

    private ArrayList<Card> cards = new ArrayList<>();

    private ArrayList<Account> accounts = new ArrayList<>();

    private static final String DIR_FINANCE = "Finance";


    public PhysicalPerson getPhysicalPerson() {
        return physicalPerson;
    }

    public void setPhysicalPerson(PhysicalPerson physicalPerson) {
        this.physicalPerson = physicalPerson;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }


    public PhysicalPersonProfile(Bank bank, PhysicalPerson physicalPerson) {
        super(bank);
        this.physicalPerson = physicalPerson;
    }

    // проверить привязана ли карта к профилю клиента
    public boolean isClientCard(Card card) {
        for (int idCard = 0; idCard < cards.size(); idCard++) {
            if (cards.get(idCard).equals(card)) return true;
        }
        return false;
    }

    // проверить привязан ли счёт к профилю клиента
    public boolean isClientAccount(Account account) {
        for (int idAccount = 0; idAccount < accounts.size(); idAccount++) {
            if (accounts.get(idAccount).equals(account)) return true;
        }
        return false;
    }

    // Прибавить сумму перевода на карту к общей сумме совершённых оплат и переводов в сутки, чтобы контролировать лимиты
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, Card toCard) {
        // моя ли карта, на которую выполняем перевод
        boolean isMyCard = isClientCard(toCard);
        // если не моя карта, то обновляем общую сумму
        if (!isMyCard) updateTotalPaymentsTransfersDay(sum, fromCurrencyCode);
    }

    // Прибавить сумму перевода на счёт к общей сумме совершённых оплат и переводов в сутки, чтобы контролировать лимиты
    public void updateTotalPaymentsTransfersDay(float sum, String fromCurrencyCode, Account toAccount) {
        // мой ли счёт, на который выполняем перевод
        boolean isMyAccount = isClientAccount(toAccount);
        // если не мой счёт, то обновляем общую сумму
        if (!isMyAccount) updateTotalPaymentsTransfersDay(sum, fromCurrencyCode);
    }

    @Override
    // Вывод всех операций по всем картам и счетам профиля физического лица
    public void displayProfileTransactions() {
        String paymentsTransfersDayInRUB = "Платежей и переводов за текущие сутки выполнено на сумму: " + getTotalPaymentsTransfersDayInRUB() +
                "₽ Доступный лимит: " + (getLimitPaymentsTransfersDayInRUB() - getTotalPaymentsTransfersDayInRUB()) + "₽ из " +
                getLimitPaymentsTransfersDayInRUB() + "₽";

        System.out.println(paymentsTransfersDayInRUB);
        IOFile.write(getPathToTransactionHistoryFile(), paymentsTransfersDayInRUB, true);

        // для подсчёта всех транзакций по всем счетам и картам клиента
        int countAllTransactions = 0;

        // подсчитать общее количество всех транзакций по всем счетам
        for (int idAccount = 0; idAccount < accounts.size(); idAccount++) {
            countAllTransactions += accounts.get(idAccount).getAllAccountTransactions().length;
        }

        // и объявить массив всех транзакций профиля клиента длиной равной количеству всех транзакций
        String[] allTransactions = new String[countAllTransactions];

        // теперь нужно перебрать все счета
        int destPos = 0;
        for (int idAccount = 0; idAccount < accounts.size(); idAccount++) {
            String[] allAccountTransactions = accounts.get(idAccount).getAllAccountTransactions();
            System.arraycopy(allAccountTransactions, 0, allTransactions, destPos, allAccountTransactions.length);
            destPos += allAccountTransactions.length;
        }

        // далее нужно отсортировать все транзакции по дате и времени
        Arrays.sort(allTransactions);

        // и осталось вывести все транзакции
        for (int idTransaction = 0; idTransaction < countAllTransactions; idTransaction++) {
            String transaction = "#" + (idTransaction + 1) + " " + allTransactions[idTransaction];
            System.out.println(transaction);
            IOFile.write(getPathToTransactionHistoryFile(), transaction, true);
        }

        System.out.println();
        IOFile.write(getPathToTransactionHistoryFile(), "\n", true);

    }

    public String getPathToTransactionHistoryFile() {
        File dirFinance = new File(DIR_FINANCE);
        if (!dirFinance.exists()) dirFinance.mkdir();
        String pathToTransactionHistoryFile = DIR_FINANCE + File.separator + getBank().getBankName() + "_" +
                physicalPerson.getFirstName() + "_" + physicalPerson.getLastName() +".txt";
        return pathToTransactionHistoryFile;
    }

}
