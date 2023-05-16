package ru.geekstar.Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormTransactions {
    private JPanel panelTransactions;
    private JButton buttonCancel;
    private JPanel panelTransactionsList;
    private JScrollPane scrollPaneTransactionsList;

    public JPanel getPanelTransactions() {
        return panelTransactions;
    }


    public FormTransactions() {
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }

}
