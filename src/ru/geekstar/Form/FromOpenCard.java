package ru.geekstar.Form;

import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Account.TinkoffPayCardAccount;
import ru.geekstar.Bank.Bank;
import ru.geekstar.Bank.IBankServicePhysicalPersons;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.Bank.Tinkoff;
import ru.geekstar.Card.*;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class FromOpenCard {
    private JPanel panelOpenCard;
    private JButton buttonOpenCard;
    private JButton buttonCancel;
    private JScrollPane scrollPaneCards;
    private JPanel panelCards;
    private JLabel labelNameOpenCard;
    private Class nameClassSelectedCard;

    public JPanel getPanelOpenCard() {
        return panelOpenCard;
    }

    public FromOpenCard() {
        // убрать рамку
        scrollPaneCards.setBorder(null);

        // создаём массив классов карт
        ArrayList<Class<? extends Card>> nameClassCards = new ArrayList<>();
        nameClassCards.add(SberMastercardGold.class);
        nameClassCards.add(SberMastercardTravel.class);
        nameClassCards.add(SberVisaGold.class);
        nameClassCards.add(TinkoffAirlinesMir.class);
        nameClassCards.add(TinkoffBlackMir.class);

        // создаём невидимый компонент фиксированной высоты перед первой картой для отступа от левого края формы
        panelCards.add(Box.createHorizontalStrut(15));

        // перебираем классы карт
        for (Class<? extends Card> nameClassCard : nameClassCards) {
            // создаём компонент JLabel для визуального отображения иконки и названия карты
            JLabel labelIconCard = new JLabel();
            // устанавливаем иконку для отображения карты
            ImageIcon iconCard = new ImageIcon(getClass().getResource(File.separator + "resources" + File.separator + nameClassCard.getSimpleName() + ".png"));
            labelIconCard.setIcon(iconCard);
            labelIconCard.setText(nameClassCard.getSimpleName());
            // устанавливаем выравнивание текста относительно иконки карты
            labelIconCard.setHorizontalTextPosition(JLabel.CENTER);
            labelIconCard.setVerticalTextPosition(JLabel.BOTTOM);

            // добавляем созданный labelIconCard с иконкой и названием карты на панель
            panelCards.add(labelIconCard);
            // создаём невидимый компонент фиксированной высоты между двумя компонентами для отступа
            panelCards.add(Box.createHorizontalStrut(15));

            labelIconCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // слушатель срабатывает в случае клика по labelIconCard
                    super.mouseClicked(e);
                    // при клике по карте выводим название карты, которую выбрал пользователь
                    labelNameOpenCard.setText("Хочу открыть " + labelIconCard.getText());
                    try {
                        // определяем класс карты, по которой кликнул пользователь
                        nameClassSelectedCard = Class.forName(nameClassCard.getPackageName() + "." + labelIconCard.getText());
                    } catch (ClassNotFoundException classNotFoundEx) {
                        System.out.println("Класс не найден");
                    }
                }
            });
        }

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });

        buttonOpenCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // если пользователь ещё не выбрал карту, то призываем его это сделать и прерываем выполнение метода return (выход из метода)
                if (nameClassSelectedCard == null) {
                    JOptionPane.showMessageDialog(panelOpenCard, "Выберите карту");
                    return;
                }

                Class classBank = null;
                Class classPayCardAccount = null;

                // если имя выбранной пользователем карты содержит "Sber", то определяем классы банка и счёта
                if (nameClassSelectedCard.getSimpleName().contains("Sber")) {
                    classBank = Sberbank.class;
                    classPayCardAccount = SberPayCardAccount.class;
                }
                // если имя выбранной пользователем карты содержит "Tinkoff", то определяем классы банка и счёта
                if (nameClassSelectedCard.getSimpleName().contains("Tinkoff")) {
                    classBank = Tinkoff.class;
                    classPayCardAccount = TinkoffPayCardAccount.class;
                }

                IBankServicePhysicalPersons bank = null;
                // определяем профиль физ. лица по классу банка выбранной пользователем карты
                PhysicalPersonProfile profile = FormMain.physicalPerson.getPhysicalPersonProfile(classBank);
                // если профиль не найден, то создаёи объект банка и регистрируем пользователя в банке
                if (profile == null) {
                    try {
                        // вызываем конструктор банка по умолчанию
                        bank = (IBankServicePhysicalPersons) classBank.getConstructor().newInstance();
                    } catch (NoSuchMethodException noSuchMethodEx) {
                        System.out.println("Конструктор не найден " + noSuchMethodEx.getMessage());
                    } catch (IllegalAccessException illegalAccessEx) {
                        System.out.println("Конструктор недоступен " + illegalAccessEx.getMessage());
                    } catch (InstantiationException instantiationEx) {
                        System.out.println("Невозможно создать объект абстрактного класса " + instantiationEx.getMessage());
                    } catch (InvocationTargetException invocationTargetEx) {
                        System.out.println("Вызываемый конструктор выбросил исключение " + invocationTargetEx.getMessage());
                    }
                    // регистрируем пользователя в банке и создаём для него профиль
                    FormMain.physicalPerson.registerPhysicalPersonToBank(bank);
                // иначе получаем объект банка из профиля
                } else bank = (IBankServicePhysicalPersons) profile.getBank();

                // вызваем метод банка для создания объекта карты, то есть для открытия карты
                FormMain.physicalPerson.openCard(bank, nameClassSelectedCard, classPayCardAccount,"RUB", Bank.generatePinCode());
                // отображаем панель со статусом операции
                FormMain.formMain.displayPanelStatus("Карта открыта");
            }
        });

        // слушатель срабатывает в момент показа компонента panelOpenCard
        panelOpenCard.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                // сбрасываем до значений по умолчанию
                labelNameOpenCard.setText("Выберите карту двойным кликом");
                nameClassSelectedCard = null;
            }
        });
    }



}
