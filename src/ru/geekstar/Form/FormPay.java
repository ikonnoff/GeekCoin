package ru.geekstar.Form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }
}
