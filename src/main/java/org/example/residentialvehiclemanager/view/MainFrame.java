package org.example.residentialvehiclemanager.view;

import org.example.residentialvehiclemanager.controller.AuthenticationController;
import org.example.residentialvehiclemanager.controller.ServiceController;
import org.example.residentialvehiclemanager.view.panels.*;

import javax.swing.*;
import java.awt.*;

/**
 * Frame Principal
 * Ventana principal del sistema con navegación y panel central
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private ServiceController serviceController;
    private AuthenticationController authController;
    private JLabel usuarioLabel;
    private JLabel estadisticasLabel;

    public MainFrame() {
        this.serviceController = ServiceController.getInstance();
        this.authController = AuthenticationController.getInstance();
        initializeUI();
        cargarEstadisticas();
    }

    private void initializeUI() {
        setTitle("Sistema de Gestión de Vehículos - Consorcio Residencial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Barra superior con información del usuario
        JPanel topPanel = crearBarraSuperior();
        add(topPanel, BorderLayout.NORTH);

        // Panel con pestañas
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Solo mostrar pestañas según el rol
        agregarPestañas();

        add(tabbedPane, BorderLayout.CENTER);

        // Barra inferior
        JPanel bottomPanel = crearBarraInferior();
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel crearBarraSuperior() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185));
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Título
        JLabel titleLabel = new JLabel("📋 Sistema de Gestión de Vehículos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);

        // Panel de información del usuario
        JPanel userPanel = new JPanel();
        userPanel.setOpaque(false);
        userPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));

        usuarioLabel = new JLabel("Usuario: " + authController.getUsuarioActual().getNombreUsuario());
        usuarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        usuarioLabel.setForeground(Color.WHITE);

        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        logoutButton.setBackground(new Color(192, 57, 43));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(e -> cerrarSesion());

        userPanel.add(usuarioLabel);
        userPanel.add(logoutButton);
        panel.add(userPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearBarraInferior() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 73, 94));
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        estadisticasLabel = new JLabel();
        estadisticasLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        estadisticasLabel.setForeground(Color.WHITE);

        panel.add(estadisticasLabel, BorderLayout.WEST);

        return panel;
    }

    private void agregarPestañas() {
        if (authController.esAdministrador()) {
            // Administrador tiene acceso a todo
            tabbedPane.addTab("🏢 Torres", new TorresPanel(serviceController, this));
            tabbedPane.addTab("🏠 Inmuebles", new InmueblesPanel(serviceController, this));
            tabbedPane.addTab("👥 Residentes", new ResidentesPanel(serviceController, this));
            tabbedPane.addTab("🚗 Vehículos", new VehiculosPanel(serviceController, this));
            tabbedPane.addTab("👤 Usuarios", new UsuariosPanel(serviceController, this));
            tabbedPane.addTab("📝 Registros", new RegistrosAccessoPanel(serviceController, this));
            tabbedPane.addTab("🔍 Consultas", new ConsultasPanel(serviceController, this));
        } else if (authController.esPortero()) {
            // Portero solo registra accesos
            tabbedPane.addTab("🚗 Registro Vehículos", new RegistroVehiculoPanel(serviceController, this));
            tabbedPane.addTab("📝 Mi Historial", new RegistrosAccessoPanel(serviceController, this));
        }
    }

    private void cargarEstadisticas() {
        new Thread(() -> {
            try {
                int torres = serviceController.obtenerCantidadTorres();
                int inmuebles = serviceController.obtenerCantidadInmuebles();
                int residentes = serviceController.obtenerCantidadResidentes();
                int vehiculos = serviceController.obtenerCantidadVehiculos();

                SwingUtilities.invokeLater(() -> {
                    estadisticasLabel.setText(String.format(
                            "Torres: %d | Inmuebles: %d | Residentes: %d | Vehículos: %d",
                            torres, inmuebles, residentes, vehiculos
                    ));
                });
            } catch (Exception e) {
                System.err.println("Error al cargar estadísticas: " + e.getMessage());
            }
        }).start();
    }

    private void cerrarSesion() {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar la sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            authController.cerrarSesion();
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame(() ->
                    SwingUtilities.invokeLater(MainFrame::new)
            ));
        }
    }
}
