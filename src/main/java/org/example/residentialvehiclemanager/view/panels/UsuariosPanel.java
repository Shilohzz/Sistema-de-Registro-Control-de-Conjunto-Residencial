package org.example.residentialvehiclemanager.view.panels;

import org.example.residentialvehiclemanager.controller.ServiceController;

import javax.swing.*;
import java.awt.*;

public class UsuariosPanel extends JPanel {
    private ServiceController serviceController;
    private JFrame parentFrame;

    public UsuariosPanel(ServiceController serviceController, JFrame parentFrame) {
        this.serviceController = serviceController;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Panel de Usuarios - En desarrollo");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
