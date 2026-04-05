package org.example.residentialvehiclemanager.view.panels;



import org.example.residentialvehiclemanager.controller.ServiceController;
import org.example.residentialvehiclemanager.model.Torre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Panel para gestionar Torres
 */
public class TorresPanel extends JPanel {
    private ServiceController serviceController;
    private JFrame parentFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nombreTorreField;
    private JButton agregarButton, actualizarButton, eliminarButton;
    private int filaSeleccionada = -1;

    public TorresPanel(ServiceController serviceController, JFrame parentFrame) {
        this.serviceController = serviceController;
        this.parentFrame = parentFrame;
        initializeUI();
        cargarTorres();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // Panel superior para agregar/editar
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Crear/Editar Torre"));
        inputPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelNombre = new JLabel("Nombre Torre:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(labelNombre, gbc);

        nombreTorreField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(nombreTorreField, gbc);

        agregarButton = new JButton("Agregar");
        agregarButton.addActionListener(e -> agregarTorre());
        gbc.gridx = 2;
        inputPanel.add(agregarButton, gbc);

        actualizarButton = new JButton("Actualizar");
        actualizarButton.addActionListener(e -> actualizarTorre());
        gbc.gridx = 3;
        inputPanel.add(actualizarButton, gbc);

        eliminarButton = new JButton("Eliminar");
        eliminarButton.setBackground(new Color(231, 76, 60));
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.addActionListener(e -> eliminarTorre());
        gbc.gridx = 4;
        inputPanel.add(eliminarButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Tabla
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre Torre", "Inmuebles", "Vehículos"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filaSeleccionada = table.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    nombreTorreField.setText(tableModel.getValueAt(filaSeleccionada, 1).toString());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarTorres() {
        new Thread(() -> {
            try {
                List<Torre> torres = serviceController.obtenerTodasLasTorres();
                tableModel.setRowCount(0);

                for (Torre torre : torres) {
                    try {
                        int inmuebles = 0; // Aquí podrías obtener la cantidad
                        int vehiculos = 0;
                        tableModel.addRow(new Object[]{
                                torre.getIdTorre(),
                                torre.getNombreTorre(),
                                inmuebles,
                                vehiculos
                        });
                    } catch (Exception e) {
                        System.err.println("Error al procesar torre: " + e.getMessage());
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar torres: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    private void agregarTorre() {
        String nombre = nombreTorreField.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para la torre",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new Thread(() -> {
            try {
                serviceController.crearTorre(nombre);
                JOptionPane.showMessageDialog(this, "Torre agregada exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                nombreTorreField.setText("");
                filaSeleccionada = -1;
                cargarTorres();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar torre: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    private void actualizarTorre() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una torre para actualizar",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = nombreTorreField.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para la torre",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(filaSeleccionada, 0);

        new Thread(() -> {
            try {
                serviceController.actualizarTorre(id, nombre);
                JOptionPane.showMessageDialog(this, "Torre actualizada exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                nombreTorreField.setText("");
                filaSeleccionada = -1;
                cargarTorres();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar torre: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    private void eliminarTorre() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una torre para eliminar",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(filaSeleccionada, 0);
        int option = JOptionPane.showConfirmDialog(this,
                "¿Eliminar esta torre y todos sus datos asociados?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            new Thread(() -> {
                try {
                    serviceController.eliminarTorre(id);
                    JOptionPane.showMessageDialog(this, "Torre eliminada exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    nombreTorreField.setText("");
                    filaSeleccionada = -1;
                    cargarTorres();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar torre: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }).start();
        }
    }
}