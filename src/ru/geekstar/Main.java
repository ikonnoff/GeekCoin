package ru.geekstar;

import ru.geekstar.Account.SberSavingsAccount;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.Card.SberMastercardGold;
import ru.geekstar.Card.SberMastercardTravel;
import ru.geekstar.Card.SberVisaGold;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public class Main {

    public static void main(String[] args) {

        PhysicalPerson I = new PhysicalPerson();
        I.setFirstName("Игорь");
        I.setLastName("Коннов");
        I.setTelephone("+79277394841");
        I.setAge((byte)32);
        I.setGender('M');

        PhysicalPerson friend = new PhysicalPerson();
        friend.setFirstName("Герман");
        friend.setLastName("Греф");
        friend.setTelephone("+79273547845");
        friend.setAge((byte)58);
        friend.setGender('M');

        Sberbank sberbank = new Sberbank();
        sberbank.setBankName("Сбер");

        I.registerPhysicalPersonToBank(sberbank);
        friend.registerPhysicalPersonToBank(sberbank);

        SberVisaGold mySberVisaGold1 = (SberVisaGold) I.openCard(sberbank, new SberVisaGold(), "RUB", "7751");
        SberVisaGold mySberVisaGold2 = (SberVisaGold) I.openCard(sberbank, new SberVisaGold(), "RUB", "9462");

        SberMastercardGold sberMastercardGold = (SberMastercardGold) I.openCard(sberbank, new SberMastercardGold(), "RUB", "4837");

        SberMastercardTravel sberMastercardTravel = (SberMastercardTravel) I.openCard(sberbank, new SberMastercardTravel(), "RUB", "3957");
        I.addAccountToMulticurrencyCard(sberbank, sberMastercardTravel, "USD");
        I.switchAccountOfMulticurrencyCard(sberMastercardTravel, "USD");

        SberSavingsAccount mySberSavingsAccount1 = (SberSavingsAccount) I.openAccount(sberbank, new SberSavingsAccount(), "RUB");
        SberSavingsAccount mySberSavingsAccount2 = (SberSavingsAccount) I.openAccount(sberbank, new SberSavingsAccount(), "RUB");

        SberVisaGold friendSberVisaGold1 = (SberVisaGold) friend.openCard(sberbank, new SberVisaGold(), "RUB", "1538");

        I.depositingCash2Card(sberMastercardTravel, 8300.00f);

        I.depositingCash2Card(mySberVisaGold1, 7600.50f);
        I.depositingCash2Card(sberMastercardGold, 2000.00f);

        I.payByCard(sberMastercardTravel, 3700.00f, "Bike", "Турция");
        I.payByCard(mySberVisaGold1, 100.50f, "ЖКХ", "7751");
        I.payByCard(sberMastercardGold, 700.00f, "Пятёрочка", "4837");

        I.payByCard(mySberVisaGold1, 110.00f, "Excursion", "Турция", "7751");
        I.payByCard(sberMastercardGold, 200.00f, "Attraction", "Турция", "4837");
        I.payByCardBonuses(mySberVisaGold1, 157.00f, 10, "Starbucks");

        I.transferCard2Card(mySberVisaGold1, mySberVisaGold2, 250.00f);
        I.transferCard2Card(mySberVisaGold1, friendSberVisaGold1, 55.00f);
        I.transferCard2Card(sberMastercardGold, mySberVisaGold1, 100.00f);
        I.transferCard2Card(mySberVisaGold1, sberMastercardGold, 107.00f);

        I.transferCard2Account(mySberVisaGold1, mySberSavingsAccount1, 95.00f);
        I.transferCard2Account(sberMastercardGold, mySberSavingsAccount1, 57.00f);

        I.transferAccount2Card(mySberSavingsAccount1, mySberVisaGold1, 15.00f);
        I.transferAccount2Card(mySberSavingsAccount1, sberMastercardGold, 33.00f);
        I.transferAccount2Account(mySberSavingsAccount1, mySberSavingsAccount2, 50.00f);

        I.depositingCardFromCard(mySberVisaGold1, mySberVisaGold2, 145.00f);
        I.depositingCardFromCard(sberMastercardGold, mySberVisaGold1, 77.00f);
        I.depositingCardFromAccount(mySberVisaGold1, mySberSavingsAccount1, 75.00f);
        I.depositingCardFromAccount(sberMastercardGold, mySberSavingsAccount1, 53.00f);

        I.depositingAccountFromCard(mySberSavingsAccount1, mySberVisaGold1, 350.00f);
        I.depositingAccountFromCard(mySberSavingsAccount2, sberMastercardGold, 105.00f);
        I.depositingAccountFromAccount(mySberSavingsAccount1, mySberSavingsAccount2, 25.00f);

/*
        System.out.println("Вывод операций по карте " + sberMastercardGold.getNumberCard());
        I.displayCardTransactions(sberMastercardGold);

        System.out.println("\nВывод операций по карте друга " + friendSberVisaGold1.getNumberCard());
        I.displayCardTransactions(friendSberVisaGold1);

        System.out.println("\nВывод операций по карте " + mySberVisaGold2.getNumberCard());
        I.displayCardTransactions(mySberVisaGold2);

        System.out.println("\nВывод операций по счету " + mySberSavingsAccount1.getNumberAccount());
        I.displayAccountTransactions(mySberSavingsAccount1);

        System.out.println("\nВывод операций по платёжному счёту карты " + mySberVisaGold1.getPayCardAccount());
        I.displayAccountTransactions(mySberVisaGold2.getPayCardAccount());
*/

        // Вывод всех операций по всем картам и счетам профиля клиента с сортировкой по дате и времени
        I.displayProfileTransactions();
        friend.displayProfileTransactions();

    }
}