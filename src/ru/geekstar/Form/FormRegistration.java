package ru.geekstar.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
        hintDisplay(textFieldSurname, SURNAME);
        hintDisplay(textFieldName, NAME);
        hintDisplay(textFieldLogin, LOGIN);
        hintDisplay(passwordField, PASS);
        hintDisplay(textFieldTelephone, TELEPHONE);

        ButtonGroup buttonGroupGender = new ButtonGroup();
        buttonGroupGender.add(radioButtonWoman);
        buttonGroupGender.add(radioButtonMan);

        buttonRegistration.setEnabled(false);

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

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });

    }

    public void hintClear(JTextField textField, String hint) {
        if (textField.getText().equals(hint)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }
    }

    public void hintDisplay(JTextField textField, String hint) {
        if (textField.getText().equals("")) {
            textField.setText(hint);
            textField.setForeground(Color.GRAY);
        }
    }

}
