package ru.geekstar.Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormTransfer {
    private JPanel panelTransfer;
    private JComboBox comboBoxFrom;
    private JComboBox comboBoxTo;
    private JTextField textFieldSum;
    private JButton buttonTrnasfer;
    private JButton buttonCancel;

    public JPanel getPanelTransfer() {
        return panelTransfer;
    }


    public FormTransfer() {
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }
}
