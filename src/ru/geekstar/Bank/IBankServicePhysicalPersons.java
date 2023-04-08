package ru.geekstar.Bank;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Card.Card;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

import java.lang.reflect.InvocationTargetException;

public interface IBankServicePhysicalPersons {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    // Открыть карту
    default Card openCard(PhysicalPersonProfile physicalPersonProfile, Class<? extends Card> classCard, Class<? extends PayCardAccount> classPayCardAccount, String currencyCode, String pinCode) {

        //открыть платёжный счёт
        PayCardAccount bankPayCardAccount = (PayCardAccount) openAccount(physicalPersonProfile, classPayCardAccount, currencyCode);

        Card card = null;
        try {
            card = classCard.getConstructor(PhysicalPersonProfile.class, PayCardAccount.class, String.class)
                    .newInstance(physicalPersonProfile, bankPayCardAccount, pinCode);
        } catch (NoSuchMethodException noSuchMethodEx) {
            System.out.println("Конструктор не найден " + noSuchMethodEx.getMessage());
        } catch (IllegalAccessException illegalAccessEx) {
            System.out.println("Конструктор недоступен " + illegalAccessEx.getMessage());
        } catch (InstantiationException instantiationEx) {
            System.out.println("Невозможно создать объект абстрактного класса " + instantiationEx.getMessage());
        } catch (InvocationTargetException invocationTargetEx) {
            System.out.println("Вызываемый конструктор выбросил исключение " + invocationTargetEx.getMessage());
        }

        // привязать карту к платёжному счёту
        bankPayCardAccount.getCards().add(card);

        // привязать карту к профилю клиента
        physicalPersonProfile.getCards().add(card);

        return card;
    }

    // Открыть счёт
    default Account openAccount(PhysicalPersonProfile physicalPersonProfile, Class<? extends Account> classAccount, String currencyCode) {
        Account account = null;
        try {
            account = classAccount.getConstructor(PhysicalPersonProfile.class, String.class)
                    .newInstance(physicalPersonProfile, currencyCode);
        } catch (NoSuchMethodException noSuchMethodEx) {
            System.out.println("Конструктор не найден " + noSuchMethodEx.getMessage());
        } catch (IllegalAccessException illegalAccessEx) {
            System.out.println("Конструктор недоступен " + illegalAccessEx.getMessage());
        } catch (InstantiationException instantiationEx) {
            System.out.println("Невозможно создать объект абстрактного класса " + instantiationEx.getMessage());
        } catch (InvocationTargetException invocationTargetEx) {
            System.out.println("Вызываемый конструктор выбросил исключение " + invocationTargetEx.getMessage());
        }

        // привязать сберегательный счёт к профилю клиента
        physicalPersonProfile.getAccounts().add(account);

        return account;
    }

}
