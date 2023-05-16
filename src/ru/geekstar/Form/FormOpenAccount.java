package ru.geekstar.Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormOpenAccount {
    private JPanel panelOpenAccount;
    private JComboBox comboBoxBank;
    private JComboBox comboBoxCurrency;
    private JButton buttonOpenAccount;
    private JButton buttonCancel;

    public JPanel getPanelOpenAccount() {
        return panelOpenAccount;
    }


    public FormOpenAccount() {
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }

}
