package org.example.residentialvehiclemanager;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.controller.AuthenticationController;
import org.example.residentialvehiclemanager.view.LoginFrame;
import org.example.residentialvehiclemanager.view.MainFrame;
import javax.swing.*;

/**
 * Clase Main - Punto de entrada de la aplicación
 * Inicializa la base de datos y muestra la ventana de login
 */
public class Application {
    public static void main(String[] args) {
        // Configurar Look & Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al configurar Look & Feel: " + e.getMessage());
        }

        // Inicializar en EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("╔════════════════════════════════════════════════════╗");
                System.out.println("║  Sistema de Gestión de Vehículos Residencial      ║");
                System.out.println("║  Versión 1.0.0                                    ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                System.out.println();

                // Inicializar base de datos
                System.out.println("Inicializando sistema...");
                DatabaseManager dbManager = DatabaseManager.getInstance();
                System.out.println("✓ Base de datos lista");

                // Mostrar ventana de login
                new LoginFrame(() -> {
                    SwingUtilities.invokeLater(MainFrame::new);
                });

            } catch (Exception e) {
                System.err.println("✗ Error fatal al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación:\n" + e.getMessage(),
                        "Error Fatal",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}