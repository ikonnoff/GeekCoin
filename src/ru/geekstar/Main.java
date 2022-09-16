package ru.geekstar;

public class Main {

    public static void main(String[] args) {

        Card visaCard = new Card();
        visaCard.setDeposit(7100.00f);
        visaCard.setNumberCard("4573 6847 3497 1284");
        visaCard.setPaySystem("VISA");
        visaCard.setCurrency('₽');
        visaCard.pay(100.50f);
        visaCard.pay(250.00f);
        visaCard.transfer(100.00f);

        //System.out.println("\nОперации по карте " + visaCard.getPaySystem() + " " + visaCard.getNumberCard() + ": ");
        String[] transactions = visaCard.getTransactions();
        int countTransactions = visaCard.getCountTransactions();
        for (int id = 0; id < countTransactions; id++) {
            //System.out.println("Операция #" + id + " по карте " + transactions[id]);
        }

        Card masterCard = new Card();
        masterCard.setDeposit(5600.00f);
        masterCard.setNumberCard("7836 7562 5734 8693");
        masterCard.setPaySystem("MASTERCARD");
        masterCard.setCurrency('$');
        masterCard.pay(50.25f);

        Card unionPayCard = new Card();
        unionPayCard.setDeposit(10000.00f);
        unionPayCard.setNumberCard("4573 8695 8473 6743");
        unionPayCard.setPaySystem("UNIONPAY");
        unionPayCard.setCurrency('€');
        unionPayCard.pay(500.00f);

        // Создадим массив карт
        Card[] cards = new Card[3];
        cards[0] = visaCard;
        cards[1] = masterCard;
        cards[2] = unionPayCard;

        cards[0].pay(100.00f);
        cards[1].transfer(200.00f);
        cards[2].transfer(1000.00f);

        System.out.println("Операции по всем картам: ");
        for (int idCard = 0; idCard < cards.length; idCard++) {
            Card card = cards[idCard];
            String[] cardTransactions = card.getTransactions();
            int cardCountTransactions = card.getCountTransactions();

            for (int id = 0; id < cardCountTransactions; id++) {
                System.out.println("Операция #" + id + " по карте " + cardTransactions[id]);
            }

        }
    }
}
