package org.example.residentialvehiclemanager.view.panels;

import org.example.residentialvehiclemanager.controller.ServiceController;

import javax.swing.*;
import java.awt.*;

/**
 * Paneles vacíos para expansión futura - Se implementan con el mismo patrón que TorresPanel
 */
public class InmueblesPanel extends JPanel {
    private ServiceController serviceController;
    private JFrame parentFrame;

    public InmueblesPanel(ServiceController serviceController, JFrame parentFrame) {
        this.serviceController = serviceController;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Panel de Inmuebles - En desarrollo");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
