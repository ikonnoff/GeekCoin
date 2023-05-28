package ru.geekstar.Form;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class FormStatus {
    private JPanel panelStatus;
    private JLabel labelStatus;

    public JPanel getPanelStatus() {
        return panelStatus;
    }

    public JLabel getLabelStatus() {
        return labelStatus;
    }

    public FormStatus() {
        ImageIcon iconStatus = new ImageIcon(getClass().getResource(File.separator + "resources" + File.separator + "Status.png"));
        labelStatus.setIcon(iconStatus);

        // слушатель срабатывает в момент показа компонента panelStatus
        panelStatus.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                try {
                    // панель отображается 1 секунду
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedEx) {
                    System.out.println("Статус прерванного текущего потока сбрасывается " + interruptedEx.getMessage());
                }

                // переключаемся на главную панель
                FormMain.formMain.displayPanelMain();
            }
        });

    }

}
