package ru.geekstar.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormMain extends JDialog {
    private JPanel contentPanel;
    private JButton buttonPay;
    private JButton buttonTransfer;
    private JButton buttonDepositing;
    private JButton buttonRegistration;
    private JButton buttonOpenAccount;
    private JButton buttonOpenCard;
    private JPanel panelMain;
    private JLabel labelNameClient;
    private JScrollPane scrollPaneCardsAccounts;
    private JPanel panelCardsAccounts;
    private JLabel labelDescription;
    private JComboBox comboBoxAccountsCard;
    private JLabel labelSwitchAccountForCard;
    private JButton buttonTransactions;
    private JComboBox comboBoxAddAccountForCard;
    private JLabel labelAddAccountForCard;
    private JButton buttonAddAccountForCard;

    public static FormMain formMain;
    private CardLayout cardLayout;


    public FormMain() {
        setContentPane(contentPanel);
        setModal(true);
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        FormRegistration formRegistration = new FormRegistration();
        FromOpenCard fromOpenCard = new FromOpenCard();
        FormOpenAccount formOpenAccount = new FormOpenAccount();
        FormPay formPay = new FormPay();
        FormTransfer formTransfer = new FormTransfer();
        FormDepositing formDepositing = new FormDepositing();
        FormTransactions formTransactions = new FormTransactions();

        JPanel panelRegistration = formRegistration.getPanelRegistration();
        JPanel panelOPenCard = fromOpenCard.getPanelOpenCard();
        JPanel panelOpenaccount = formOpenAccount.getPanelOpenAccount();
        JPanel panelPay = formPay.getPanelPay();
        JPanel panelTransfer = formTransfer.getPanelTransfer();
        JPanel panelDepositing = formDepositing.getPanelDepositing();
        JPanel panelTransactions = formTransactions.getPanelTransactions();

        contentPanel.add("PANEL_MAIN", panelMain);
        contentPanel.add("PANEL_REGISTRATION", panelRegistration);
        contentPanel.add("PANEL_OPEN_CARD", panelOPenCard);
        contentPanel.add("PANEL_OPEN_ACCOUNT", panelOpenaccount);
        contentPanel.add("PANEL_PAY", panelPay);
        contentPanel.add("PANEL_TRANSFER", panelTransfer);
        contentPanel.add("PANEL_DEPOSITING", panelDepositing);
        contentPanel.add("PANEL_TRANSACTIONS", panelTransactions);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        buttonRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PANEL_REGISTRATION");
            }
        });

        buttonOpenCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PANEL_OPEN_CARD");
            }
        });

        buttonDepositing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PANEL_DEPOSITING");
            }
        });

        buttonTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PANEL_TRANSFER");
            }
        });

        buttonPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PANEL_PAY");
            }
        });

        buttonTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PANEL_TRANSACTIONS");
            }
        });

        buttonOpenAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "PANEL_OPEN_ACCOUNT");
            }
        });

    }

    public void displayPanelMain() {
        cardLayout.show(contentPanel, "PANEL_MAIN");
    }

    public static void main(String[] args) {
        formMain = new FormMain();
        formMain.setTitle("GeekCoin");
        formMain.setSize(1200,700);
        formMain.setLocationRelativeTo(null);
        formMain.setVisible(true);
    }

}
