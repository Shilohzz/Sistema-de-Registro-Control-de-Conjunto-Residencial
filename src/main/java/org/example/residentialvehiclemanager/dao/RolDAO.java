package org.example.residentialvehiclemanager.dao;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.model.*;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object para la entidad Rol
 */
public class RolDAO {
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    public Rol obtenerRolPorId(int id) throws SQLException {
        String sql = "SELECT * FROM roles WHERE id_rol = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirRol(rs);
                }
            }
        }
        return null;
    }

    public List<Rol> obtenerTodosLosRoles() throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles ORDER BY nombre_rol";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                roles.add(construirRol(rs));
            }
        }
        return roles;
    }

    public Rol obtenerRolPorNombre(String nombreRol) throws SQLException {
        String sql = "SELECT * FROM roles WHERE nombre_rol = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, nombreRol);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirRol(rs);
                }
            }
        }
        return null;
    }

    private Rol construirRol(ResultSet rs) throws SQLException {
        return new Rol(
                rs.getInt("id_rol"),
                rs.getString("nombre_rol"),
                rs.getString("descripcion"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime()
        );
    }
}
