package ru.geekstar.Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FromOpenCard {
    private JPanel panelOpenCard;
    private JButton buttonOpenCard;
    private JButton buttonCancel;
    private JScrollPane scrollPaneCards;
    private JPanel panelCards;
    private JLabel labelNameOpenCard;

    public JPanel getPanelOpenCard() {
        return panelOpenCard;
    }


    public FromOpenCard() {
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }

}
