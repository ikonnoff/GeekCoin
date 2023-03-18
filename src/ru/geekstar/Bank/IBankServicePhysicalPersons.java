package ru.geekstar.Bank;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Card.Card;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public interface IBankServicePhysicalPersons {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    // Открыть карту
    default Card openCard(PhysicalPersonProfile physicalPersonProfile, Card card, PayCardAccount payCardAccount, String currencyCode, String pinCode) {
        // установить свойства карты
        card.setBank(physicalPersonProfile.getBank());
        card.setNumberCard(physicalPersonProfile.getBank().generateNumberCard());
        card.setCardHolder(physicalPersonProfile);

        //открыть платёжный счёт
        PayCardAccount bankPayCardAccount = (PayCardAccount) openAccount(physicalPersonProfile, payCardAccount, currencyCode);

        // привязать карту к платёжному счёту
        bankPayCardAccount.getCards().add(card);

        // привязать платёжный счёт к карте
        card.setPayCardAccount(bankPayCardAccount);
        card.setStatusCard("Активна");
        card.setPinCode(pinCode);

        // привязать карту к профилю клиента
        physicalPersonProfile.getCards().add(card);

        return card;
    }

    // Открыть счёт
    default Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode) {
        // установить свойства сберегательного счёта
        account.setBank(physicalPersonProfile.getBank());
        account.setNumberAccount(physicalPersonProfile.getBank().generateNumberAccount());
        account.setAccountHolder(physicalPersonProfile);
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        // привязать сберегательный счёт к профилю клиента
        physicalPersonProfile.getAccounts().add(account);

        return account;
    }

}
