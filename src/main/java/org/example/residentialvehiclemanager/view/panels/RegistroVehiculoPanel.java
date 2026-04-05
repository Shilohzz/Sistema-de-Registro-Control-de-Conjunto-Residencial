package org.example.residentialvehiclemanager.view.panels;


import org.example.residentialvehiclemanager.controller.ServiceController;
import org.example.residentialvehiclemanager.model.InformacionVehiculo;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Panel para registro de vehículos (función principal de porteros)
 */
public class RegistroVehiculoPanel extends JPanel {
    private ServiceController serviceController;
    private JFrame parentFrame;
    private JTextField placaField;
    private JTextArea informacionArea;
    private JButton buscarButton, registrarButton;
    private InformacionVehiculo vehiculoActual;

    public RegistroVehiculoPanel(ServiceController serviceController, JFrame parentFrame) {
        this.serviceController = serviceController;
        this.parentFrame = parentFrame;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // Panel superior - Búsqueda
        JPanel searchPanel = crearPanelBusqueda();
        add(searchPanel, BorderLayout.NORTH);

        // Panel central - Información
        JPanel infoPanel = crearPanelInformacion();
        add(infoPanel, BorderLayout.CENTER);

        // Panel inferior - Acciones
        JPanel actionPanel = crearPanelAcciones();
        add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Buscar Vehículo"));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel placaLabel = new JLabel("Placa del Vehículo:");
        placaLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        panel.add(placaLabel, gbc);

        placaField = new JTextField(15);
        placaField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        placaField.addActionListener(e -> buscarVehiculo());
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        panel.add(placaField, gbc);

        buscarButton = new JButton("Buscar");
        buscarButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        buscarButton.setBackground(new Color(41, 128, 185));
        buscarButton.setForeground(Color.WHITE);
        buscarButton.setFocusPainted(false);
        buscarButton.setBorderPainted(false);
        buscarButton.setPreferredSize(new Dimension(100, 30));
        buscarButton.addActionListener(e -> buscarVehiculo());
        gbc.gridx = 2;
        gbc.weightx = 0.1;
        panel.add(buscarButton, gbc);

        return panel;
    }

    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Información del Vehículo"));
        panel.setBackground(Color.WHITE);

        informacionArea = new JTextArea();
        informacionArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        informacionArea.setEditable(false);
        informacionArea.setBackground(new Color(236, 240, 241));
        informacionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        informacionArea.setText("Ingrese la placa de un vehículo para buscar");

        JScrollPane scrollPane = new JScrollPane(informacionArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        registrarButton = new JButton("Registrar Ingreso");
        registrarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registrarButton.setBackground(new Color(46, 204, 113));
        registrarButton.setForeground(Color.WHITE);
        registrarButton.setFocusPainted(false);
        registrarButton.setBorderPainted(false);
        registrarButton.setPreferredSize(new Dimension(200, 40));
        registrarButton.setEnabled(false);
        registrarButton.addActionListener(e -> registrarIngreso());

        panel.add(registrarButton);

        return panel;
    }

    private void buscarVehiculo() {
        String placa = placaField.getText().trim().toUpperCase();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una placa",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new Thread(() -> {
            try {
                InformacionVehiculo info = serviceController.obtenerInformacionVehiculo(placa);
                if (info != null) {
                    vehiculoActual = info;
                    mostrarInformacion(info);
                    registrarButton.setEnabled(true);
                } else {
                    SwingUtilities.invokeLater(() -> {
                        informacionArea.setText("❌ Vehículo no encontrado\n\nVerifique que la placa sea correcta.");
                        registrarButton.setEnabled(false);
                        vehiculoActual = null;
                    });
                }
            } catch (SQLException e) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Error al buscar vehículo: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        }).start();
    }

    private void mostrarInformacion(InformacionVehiculo info) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════\n");
        sb.append("INFORMACIÓN DEL VEHÍCULO\n");
        sb.append("═══════════════════════════════════════════════════\n\n");
        sb.append("Placa:            ").append(info.getPlaca()).append("\n");
        sb.append("Marca:            ").append(info.getMarca()).append("\n");
        sb.append("Modelo:           ").append(info.getModelo()).append("\n");
        sb.append("Color:            ").append(info.getColor()).append("\n");
        sb.append("\n───────────────────────────────────────────────────\n");
        sb.append("UBICACIÓN\n");
        sb.append("───────────────────────────────────────────────────\n\n");
        sb.append("Torre:            ").append(info.getNombreTorre()).append("\n");
        sb.append("Apartamento:      ").append(info.getNumeroApartamento()).append("\n");
        sb.append("\n───────────────────────────────────────────────────\n");
        sb.append("PROPIETARIO\n");
        sb.append("───────────────────────────────────────────────────\n\n");
        sb.append("Nombre:           ").append(info.getPropietarioNombre()).append("\n");
        sb.append("Teléfono:         ").append(info.getPropietarioTelefono()).append("\n");
        sb.append("Correo:           ").append(info.getPropietarioCorreo()).append("\n");
        sb.append("\n═══════════════════════════════════════════════════\n");

        SwingUtilities.invokeLater(() -> informacionArea.setText(sb.toString()));
    }

    private void registrarIngreso() {
        if (vehiculoActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar un vehículo",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Diálogo para observaciones
        String observaciones = JOptionPane.showInputDialog(this,
                "Ingrese observaciones (opcional):",
                "Observaciones",
                JOptionPane.PLAIN_MESSAGE);

        if (observaciones == null) {
            return; // Usuario canceló
        }

        new Thread(() -> {
            try {
                int idRegistro = serviceController.registrarAccesoVehiculo(
                        vehiculoActual.getPlaca(),
                        org.example.residentialvehiclemanager.controller.AuthenticationController.getInstance()
                                .getUsuarioActual().getIdUsuario(),
                        observaciones
                );

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            "✓ Ingreso registrado exitosamente\nRegistro ID: " + idRegistro,
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    placaField.setText("");
                    informacionArea.setText("Ingrese la placa de un vehículo para buscar");
                    registrarButton.setEnabled(false);
                    vehiculoActual = null;
                });
            } catch (SQLException e) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Error al registrar ingreso: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        }).start();
    }
}
