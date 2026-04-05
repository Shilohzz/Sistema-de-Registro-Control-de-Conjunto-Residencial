package org.example.residentialvehiclemanager.dao;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.model.Torre;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Data Access Object para la entidad Torre
 * Implementa todas las operaciones CRUD
 */
public class TorreDAO {
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * Crea una nueva torre en la base de datos
     */
    public int crearTorre(Torre torre) throws SQLException {
        String sql = "INSERT INTO torres (nombre_torre) VALUES (?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, torre.getNombreTorre());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo crear la torre");
    }

    /**
     * Obtiene una torre por su ID
     */
    public Torre obtenerTorrePorId(int id) throws SQLException {
        String sql = "SELECT * FROM torres WHERE id_torre = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Torre(
                            rs.getInt("id_torre"),
                            rs.getString("nombre_torre"),
                            rs.getTimestamp("fecha_creacion").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todas las torres
     */
    public List<Torre> obtenerTodasLasTorres() throws SQLException {
        List<Torre> torres = new ArrayList<>();
        String sql = "SELECT * FROM torres ORDER BY nombre_torre ASC";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                torres.add(new Torre(
                        rs.getInt("id_torre"),
                        rs.getString("nombre_torre"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime()
                ));
            }
        }
        return torres;
    }

    /**
     * Actualiza una torre existente
     */
    public boolean actualizarTorre(Torre torre) throws SQLException {
        String sql = "UPDATE torres SET nombre_torre = ? WHERE id_torre = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, torre.getNombreTorre());
            pstmt.setInt(2, torre.getIdTorre());
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una torre (en cascada elimina inmuebles, residentes, vehículos)
     */
    public boolean eliminarTorre(int idTorre) throws SQLException {
        String sql = "DELETE FROM torres WHERE id_torre = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idTorre);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene la cantidad de inmuebles en una torre
     */
    public int obtenerCantidadInmuebles(int idTorre) throws SQLException {
        String sql = "SELECT COUNT(*) as cantidad FROM inmuebles WHERE id_torre = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idTorre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }
        }
        return 0;
    }

    /**
     * Obtiene la cantidad total de vehículos en una torre
     */
    public int obtenerCantidadVehiculos(int idTorre) throws SQLException {
        String sql = "SELECT COUNT(*) as cantidad FROM vehiculos v " +
                "JOIN inmuebles i ON v.id_inmueble_destino = i.id_inmueble " +
                "WHERE i.id_torre = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idTorre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }
        }
        return 0;
    }

    /**
     * Verifica si una torre existe por su nombre
     */
    public boolean existeTorrePorNombre(String nombreTorre) throws SQLException {
        String sql = "SELECT COUNT(*) as cantidad FROM torres WHERE nombre_torre = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, nombreTorre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad") > 0;
                }
            }
        }
        return false;
    }
}
