package org.example.residentialvehiclemanager.view.panels;


import org.example.residentialvehiclemanager.controller.ServiceController;

import javax.swing.*;
import java.awt.*;

public class VehiculosPanel extends JPanel {
    private ServiceController serviceController;
    private JFrame parentFrame;

    public VehiculosPanel(ServiceController serviceController, JFrame parentFrame) {
        this.serviceController = serviceController;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Panel de Vehículos - En desarrollo");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
