package ru.geekstar.Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormDepositing {
    private JPanel panelDepositing;
    private JComboBox comboBoxFrom;
    private JComboBox comboBoxTo;
    private JTextField textFieldSum;
    private JButton buttonDepositing;
    private JButton buttonCancel;

    public JPanel getPanelDepositing() {
        return panelDepositing;
    }


    public FormDepositing() {
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }

}
