package ru.geekstar.Form;

import ru.geekstar.PhysicalPerson.PhysicalPerson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FormRegistration {
    private JPanel panelRegistration;
    private JButton buttonRegistration;
    private JTextField textFieldSurname;
    private JTextField textFieldName;
    private JRadioButton radioButtonWoman;
    private JRadioButton radioButtonMan;
    private JComboBox comboBoxMonth;
    private JComboBox comboBoxDay;
    private JComboBox comboBoxYear;
    private JTextField textFieldLogin;
    private JPasswordField passwordField;
    private JButton buttonCancel;
    private JCheckBox checkBoxConfirmOffer;
    private JTextField textFieldTelephone;
    private JLabel labelLinkOffer;

    private static final String SURNAME = "Фамилия";
    private static final String NAME = "Имя";
    private static final String LOGIN = "Логин";
    private static final String PASS = "*****************";
    private static final String TELEPHONE = "+79999999999";

    public JPanel getPanelRegistration() {
        return panelRegistration;
    }

    public FormRegistration() {
        // отображаем подсказки для ввода пользователя
        hintDisplay(textFieldSurname, SURNAME);
        hintDisplay(textFieldName, NAME);
        hintDisplay(textFieldLogin, LOGIN);
        hintDisplay(passwordField, PASS);
        hintDisplay(textFieldTelephone, TELEPHONE);

        // объединяем в группу кнопки для выбора пола
        ButtonGroup buttonGroupGender = new ButtonGroup();
        buttonGroupGender.add(radioButtonWoman);
        buttonGroupGender.add(radioButtonMan);

        // инициализируем список с месяцами года
        initItemsComboBoxMonth();

        // инициализируем список с годами
        initItemsComboBoxYear();

        // инициализируем список с днями
        updateItemsComboBoxDay();

        // делаем кнопку "Зарегистрироваться" неактивной
        buttonRegistration.setEnabled(false);

        // focusGained — фокус достигнут, когда пользователь кликнет по полю
        // focusLost — фокус утрачен, когда пользователь кликнет по другому полю и фокус перейдёт на другое поле
        textFieldSurname.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                hintClear(textFieldSurname, SURNAME);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                hintDisplay(textFieldSurname, SURNAME);
            }
        });

        textFieldName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                hintClear(textFieldName, NAME);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                hintDisplay(textFieldName, NAME);
            }
        });

        textFieldLogin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                hintClear(textFieldLogin, LOGIN);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                hintDisplay(textFieldLogin, LOGIN);
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                hintClear(passwordField, PASS);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                hintDisplay(passwordField, PASS);
            }
        });

        // слушатель срабатывает, когда изменено состояние элемента, то есть пользователь кликнул и выбрал элемент из списка
        checkBoxConfirmOffer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (checkBoxConfirmOffer.isSelected()) buttonRegistration.setEnabled(true);
                else buttonRegistration.setEnabled(false);
            }
        });

        textFieldTelephone.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                hintClear(textFieldTelephone, TELEPHONE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                hintDisplay(textFieldTelephone, TELEPHONE);
            }
        });

        // слушатель срабатывает в случае клика по labelLinkOffer
        labelLinkOffer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Desktop.getDesktop().browse(new URI("https://geekstar.ru"));
                } catch (URISyntaxException uriSyntaxEx) {
                    System.out.println("Проверьте на корректность URL-адрес " + uriSyntaxEx.getMessage());
                } catch (IOException ioEx) {
                    System.out.println("Браузер по умолчанию не найден " + ioEx.getMessage());
                }
            }
        });

        // если пользователь выбирает другой месяц, то пересчитываем и обновляем выпадающий список с количеством дней
        comboBoxMonth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateItemsComboBoxDay();
            }
        });

        // если пользователь выбирает другой год, то пересчитываем и обновляем выпадающий список с количеством дней
        comboBoxYear.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateItemsComboBoxDay();
            }
        });

        buttonRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // проверяем заполнены ли все обязательные поля
                boolean checkFillFields = checkFillFields();
                // если хоть одно поле не заполнено, то происходит return (выход из метода)
                if (!checkFillFields) return;

                // извлекаем данные, которые указал пользователь
                String surname = textFieldSurname.getText();
                String name = textFieldName.getText();
                String telephone = textFieldTelephone.getText();

                int year = (int) comboBoxYear.getSelectedItem();
                int month = comboBoxMonth.getSelectedIndex() + 1;
                int day = (int) comboBoxDay.getSelectedItem();
                LocalDate dateOfBirth = LocalDate.of(year, month, day);

                char gender = (radioButtonMan.isSelected()) ? 'M' : 'Ж';

                // создаём объект физ. лица
                PhysicalPerson physicalPerson = new PhysicalPerson(name, surname, telephone, dateOfBirth, gender);

                // выводим на главной форме имя и фамилию пользователя
                FormMain.formMain.initPhysicalPerson(physicalPerson);
                // отображаем панель со статусом операции
                FormMain.formMain.displayPanelStatus("Регистрация завершена");
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }

    public boolean checkFillFields() {
        // если в поле отображается подсказка, то это значит, что пользователь ничего не ввёл
        if (textFieldSurname.getText().equals(SURNAME)) {
            JOptionPane.showMessageDialog(panelRegistration, "Введите фамилию");
            return false;
        }

        if (textFieldName.getText().equals(NAME)) {
            JOptionPane.showMessageDialog(panelRegistration, "Введите имя");
            return false;
        }

        if (textFieldTelephone.getText().equals(TELEPHONE)) {
            JOptionPane.showMessageDialog(panelRegistration, "Введите телефон");
            return false;
        }

        if (!radioButtonMan.isSelected() && !radioButtonWoman.isSelected()) {
            JOptionPane.showMessageDialog(panelRegistration, "Выберите пол");
            return false;
        }

        if (textFieldLogin.getText().equals(LOGIN)) {
            JOptionPane.showMessageDialog(panelRegistration, "Введите логин");
            return false;
        }

        String pass = String.valueOf(passwordField.getPassword());
        if (pass.equals(PASS)) {
            JOptionPane.showMessageDialog(panelRegistration, "Придумайте пароль");
            return false;
        }

        return true;
    }

    public void initItemsComboBoxMonth() {
        String[] month = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        // создаём модель для полей списка
        DefaultComboBoxModel<String> modelMonth = new DefaultComboBoxModel(month);
        // устанавливаем созданную модель для списка
        comboBoxMonth.setModel(modelMonth);
        // устанавливаем текущий месяц в качестве выбранного по умолчанию
        comboBoxMonth.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
    }

    public void initItemsComboBoxYear() {
        for (int year = 1924; year <= LocalDateTime.now().getYear(); year++) {
            comboBoxYear.addItem(year);
        }
        // устанавливаем текущий год в качестве выбранного по умолчанию
        comboBoxYear.setSelectedIndex(comboBoxYear.getItemCount() - 1);
    }

    public void updateItemsComboBoxDay() {
        // в зависимости от года и месяца определяем кол-во дней в месяце
        int month = comboBoxMonth.getSelectedIndex() + 1;
        int year = (int) comboBoxYear.getSelectedItem();
        int daysMonth = LocalDate.of(year, month, 1).lengthOfMonth();

        // очищаем выпадающий список с днями месяца
        comboBoxDay.removeAllItems();

        // добавляем обновлённое кол-во дней
        for (int i = 1; i <= daysMonth; i++) {
            comboBoxDay.addItem(i);
        }
    }

    // удалить подсказку
    public void hintClear(JTextField textField, String hint) {
        // если в поле отображается подсказка, то очищаем поле и изменяем цвет текста на чёрный для ввода пользователем
        if (textField.getText().equals(hint)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }
    }

    // отобразить подсказку
    public void hintDisplay(JTextField textField, String hint) {
        // если поле пустое, то отображаем в поле подсказку и устанавливаем для неё серый цвет
        if (textField.getText().equals("")) {
            textField.setText(hint);
            textField.setForeground(Color.GRAY);
        }
    }

}
