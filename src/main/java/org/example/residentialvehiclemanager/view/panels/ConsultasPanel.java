package org.example.residentialvehiclemanager.view.panels;

import org.example.residentialvehiclemanager.controller.ServiceController;

import javax.swing.*;
import java.awt.*;

public class ConsultasPanel extends JPanel {
    private ServiceController serviceController;
    private JFrame parentFrame;

    public ConsultasPanel(ServiceController serviceController, JFrame parentFrame) {
        this.serviceController = serviceController;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Panel de Consultas Avanzadas - En desarrollo");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
