package ru.geekstar.Form;

import ru.geekstar.Card.Card;
import ru.geekstar.Card.IAirlinesCard;
import ru.geekstar.Card.IBonusCard;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.ArrayList;

public class FormPay {
    private JPanel panelPay;
    private JComboBox comboBoxFrom;
    private JTextField textFieldProduct;
    private JComboBox comboBoxCountry;
    private JTextField textFieldPinCode;
    private JButton buttonPay;
    private JButton buttonCancel;
    private JSlider sliderBonus;
    private JLabel labelSliderBonus;
    private JSlider sliderMile;
    private JLabel labelSliderMile;
    private JTextField textFieldSum;
    private JLabel labelSliderMaxBonus;
    private JLabel labelSliderMaxMile;

    public JPanel getPanelPay() {
        return panelPay;
    }

    public FormPay() {
        // делаем компоненты недоступными
        sliderBonus.setEnabled(false);
        labelSliderBonus.setEnabled(false);
        labelSliderMaxBonus.setEnabled(false);

        sliderMile.setEnabled(false);
        labelSliderMile.setEnabled(false);
        labelSliderMaxMile.setEnabled(false);

        String[] countries = {"Россия", "Турция", "Казахстан"};
        // устанавливаем в выпадающий список массив со странами
        comboBoxCountry.setModel(new DefaultComboBoxModel(countries));

        // слушатель срабатывает, когда пользователь изменяет положение ползунка, выбирая количество доступных бонусов
        sliderBonus.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelSliderBonus.setText("Списать бонусы: " + sliderBonus.getValue());
            }
        });

        // слушатель срабатывает, когда пользователь изменяет положение ползунка, выбирая количество доступных миль
        sliderMile.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelSliderMile.setText("Списать мили: " + sliderMile.getValue());
            }
        });

        // слушатель срабатывает, когда пользователь выбирает карту из списка
        comboBoxFrom.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBoxFrom.getSelectedItem() != null) {
                    hintPinCode();
                    updateSliders();
                }
            }
        });

        // слушатель срабатывает, когда пользователь выбирает страну из списка
        comboBoxCountry.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateSliders();
            }
        });

        // слушатель срабатывает, когда пользователь кликает по кнопке
        buttonPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // проверяем заполнены ли все обязательные поля
                // если хоть одно поле не заполнено, то происходит return (выход из метода)
                if (!checkFillFields()) return;

                // извлекаем данные, которые указал пользователь
                Card cardFrom = (Card) comboBoxFrom.getSelectedItem();
                float sumPay = Float.valueOf(textFieldSum.getText());
                String buyProductOrService = textFieldProduct.getText();
                String country = (String) comboBoxCountry.getSelectedItem();
                String pinCode = textFieldPinCode.getText();
                int bonuses = sliderBonus.getValue();
                int miles = sliderMile.getValue();

                // если покупка в России
                if (country.equals("Россия")) {
                    // если карта реализует интерфейс бонусной системы и пользователь указал количество бонусов для списания, то оплачиваем бонусами
                    if (cardFrom instanceof IBonusCard && bonuses > 0) FormMain.physicalPerson.payByCardBonuses((IBonusCard) cardFrom, sumPay, bonuses, buyProductOrService, pinCode);
                    // если карта реализует интерфейс накопления миль и пользователь указал количество миль для списания, то оплачиваем милями
                    else if (cardFrom instanceof IAirlinesCard && miles > 0) FormMain.physicalPerson.payByCardMiles((IAirlinesCard) cardFrom, sumPay, miles, buyProductOrService, pinCode);
                    // иначе оплачиваем полную стоимость
                    else FormMain.physicalPerson.payByCard(cardFrom, sumPay, buyProductOrService, pinCode);
                }
                // иначе оплата за границей
                else FormMain.physicalPerson.payByCard(cardFrom, sumPay, buyProductOrService, country, pinCode);

                // отображаем панель со статусом операции
                FormMain.formMain.displayPanelStatus("Оплата обрабатывается");
            }
        });

        // слушатель срабатывает в момент показа компонента panelPay
        panelPay.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                // очищаем список с картами
                comboBoxFrom.removeAllItems();
                // очищаем поля
                textFieldProduct.setText("");
                textFieldSum.setText("");

                // запрашиваем профили пользователя
                ArrayList<PhysicalPersonProfile> profiles = FormMain.physicalPerson.getPhysicalPersonProfiles();
                // перебираем профили пользователя
                for (PhysicalPersonProfile profile : profiles) {
                    // запрашиваем карты профиля пользователя
                    ArrayList<Card> cards = profile.getCards();
                    // перебираем карты
                    for (Card card : cards) {
                        // добавляем в список карты для списания
                        comboBoxFrom.addItem(card);
                    }
                }
                // отображаем пин-код для удобства
                hintPinCode();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });

    }

    public void updateSliders() {
        // делаем компоненты недоступными
        sliderBonus.setEnabled(false);
        labelSliderBonus.setEnabled(false);
        labelSliderMaxBonus.setEnabled(false);

        sliderMile.setEnabled(false);
        labelSliderMile.setEnabled(false);
        labelSliderMaxMile.setEnabled(false);

        int bonuses = 0;
        int miles = 0;

        // в зависимости от выбранной пользоватлем страны
        String country = (String) comboBoxCountry.getSelectedItem();
        // и карты списания
        Card cardFrom = (Card) comboBoxFrom.getSelectedItem();

        // если покупка в России
        if (country.equals("Россия") && cardFrom.getPayCardAccount().getCurrencyCode().equals("RUB")) {
            // если карта реализует интерфейс бонусной системы, то отображаем данные о бонусах
            if (cardFrom instanceof IBonusCard) {
                // явно приводим к типу интерфейса бонусной системы
                IBonusCard bonusCardFrom = (IBonusCard) cardFrom;
                // запрашиваем количество бонусов
                bonuses = bonusCardFrom.getBonuses();

                // делаем компоненты для указания бонусов доступными
                sliderBonus.setEnabled(true);
                labelSliderBonus.setEnabled(true);
                labelSliderMaxBonus.setEnabled(true);
            }

            // если карта реализует интерфейс накопления миль, то отображаем данные о милях
            if (cardFrom instanceof IAirlinesCard) {
                IAirlinesCard airlinesCardFrom = (IAirlinesCard) cardFrom;
                miles = airlinesCardFrom.getMiles();

                sliderMile.setEnabled(true);
                labelSliderMile.setEnabled(true);
                labelSliderMaxMile.setEnabled(true);
            }
        }

        // устанавливаем положение ползунка
        sliderBonus.setValue(0);
        // устанавливаем максимально допустимое количество бонусов для ползунка
        sliderBonus.setMaximum(bonuses);
        // отображаем количество доступных бонусов
        labelSliderMaxBonus.setText("Доступно бонусов: " + bonuses);

        sliderMile.setValue(0);
        sliderMile.setMaximum(miles);
        labelSliderMaxMile.setText("Доступно миль: " + miles);
    }

    public void hintPinCode() {
        if (comboBoxFrom.getSelectedItem() != null) {
            Card cardFrom = (Card) comboBoxFrom.getSelectedItem();
            textFieldPinCode.setText(cardFrom.getPinCode());
        }
    }

    public boolean checkFillFields() {
        if (textFieldProduct.getText().isBlank()) {
            JOptionPane.showMessageDialog(panelPay, "Введите что покупаем");
            return false;
        }

        if (comboBoxFrom.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(panelPay, "Выберите откуда списать");
            return false;
        }

        if (textFieldSum.getText().isBlank()) {
            JOptionPane.showMessageDialog(panelPay, "Введите сумму оплаты");
            return false;
        }

        // регулярное выражение для проверки суммы на её корректность
        // целая цасть может быть от 1 до 6 символов {1,6}, то есть сумма не может быть больше 999999
        // дробная часть не обязательна (?), должна содержать символ точка "." и не может превышать 2 символов {2}, то есть не может быть больше 99
        String regexSum = "[0-9]{1,6}(\\.[0-9]{2})?";
        if (!textFieldSum.getText().matches(regexSum)) {
            JOptionPane.showMessageDialog(panelPay, "Сумма может состоять из цифр и точки.\nСумма не может быть больше или равна 1 млн.");
            return false;
        }

        if (Float.valueOf(textFieldSum.getText()) == 0) {
            JOptionPane.showMessageDialog(panelPay, "Введите сумму больше 0");
            return false;
        }

        if (textFieldPinCode.getText().isBlank()) {
            JOptionPane.showMessageDialog(panelPay, "Введите PIN-код");
            return false;
        }

        return true;
    }
}
