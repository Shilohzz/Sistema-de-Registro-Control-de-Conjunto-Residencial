package org.example.residentialvehiclemanager.dao;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.model.*;
import java.sql.*;
import java.util.*;

/**
 * Data Access Object para la entidad RegistroAcceso
 */
public class RegistroAccesoDAO {
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    public int crearRegistroAcceso(RegistroAcceso registro) throws SQLException {
        String sql = "INSERT INTO registros_acceso (fecha_hora_ingreso, placa_vehiculo, " +
                "id_usuario_porteria, observaciones) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(registro.getFechaHoraIngreso()));
            pstmt.setString(2, registro.getPlacaVehiculo());
            pstmt.setInt(3, registro.getIdUsuarioPorteria());
            pstmt.setString(4, registro.getObservaciones());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo crear el registro de acceso");
    }

    public RegistroAcceso obtenerRegistroPorId(int id) throws SQLException {
        String sql = "SELECT * FROM registros_acceso WHERE id_registro = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirRegistro(rs);
                }
            }
        }
        return null;
    }

    public List<RegistroAcceso> obtenerTodosLosRegistros() throws SQLException {
        List<RegistroAcceso> registros = new ArrayList<>();
        String sql = "SELECT * FROM registros_acceso ORDER BY fecha_hora_ingreso DESC";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                registros.add(construirRegistro(rs));
            }
        }
        return registros;
    }

    public List<RegistroAcceso> obtenerRegistrosPorVehiculo(String placa) throws SQLException {
        List<RegistroAcceso> registros = new ArrayList<>();
        String sql = "SELECT * FROM registros_acceso WHERE placa_vehiculo = ? " +
                "ORDER BY fecha_hora_ingreso DESC LIMIT 100";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, placa);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(construirRegistro(rs));
                }
            }
        }
        return registros;
    }

    public List<RegistroAcceso> obtenerRegistrosPorUsuario(int idUsuario) throws SQLException {
        List<RegistroAcceso> registros = new ArrayList<>();
        String sql = "SELECT * FROM registros_acceso WHERE id_usuario_porteria = ? " +
                "ORDER BY fecha_hora_ingreso DESC LIMIT 100";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(construirRegistro(rs));
                }
            }
        }
        return registros;
    }

    /**
     * Obtiene registros en un rango de fechas
     */
    public List<RegistroAcceso> obtenerRegistrosPorFecha(java.time.LocalDateTime fechaInicio,
                                                         java.time.LocalDateTime fechaFin) throws SQLException {
        List<RegistroAcceso> registros = new ArrayList<>();
        String sql = "SELECT * FROM registros_acceso WHERE fecha_hora_ingreso BETWEEN ? AND ? " +
                "ORDER BY fecha_hora_ingreso DESC";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            pstmt.setTimestamp(2, Timestamp.valueOf(fechaFin));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(construirRegistro(rs));
                }
            }
        }
        return registros;
    }

    public List<RegistroAcceso> obtenerRegistrosRecientes(int cantidadRegistros) throws SQLException {
        List<RegistroAcceso> registros = new ArrayList<>();
        String sql = "SELECT * FROM registros_acceso ORDER BY fecha_hora_ingreso DESC LIMIT ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, cantidadRegistros);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(construirRegistro(rs));
                }
            }
        }
        return registros;
    }

    public boolean eliminarRegistro(int idRegistro) throws SQLException {
        String sql = "DELETE FROM registros_acceso WHERE id_registro = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idRegistro);
            return pstmt.executeUpdate() > 0;
        }
    }

    private RegistroAcceso construirRegistro(ResultSet rs) throws SQLException {
        return new RegistroAcceso(
                rs.getInt("id_registro"),
                rs.getTimestamp("fecha_hora_ingreso").toLocalDateTime(),
                rs.getString("placa_vehiculo"),
                rs.getInt("id_usuario_porteria"),
                rs.getString("observaciones")
        );
    }
}
