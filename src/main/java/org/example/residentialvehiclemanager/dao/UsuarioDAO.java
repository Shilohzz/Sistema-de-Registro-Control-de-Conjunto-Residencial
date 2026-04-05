package org.example.residentialvehiclemanager.dao;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.model.*;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object para la entidad Usuario
 */
public class UsuarioDAO {
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    public int crearUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_usuario, password, id_rol, activo) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setInt(3, usuario.getIdRol());
            pstmt.setBoolean(4, usuario.isActivo());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo crear el usuario");
    }

    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }
        return null;
    }

    /**
     * Autentica un usuario verificando usuario y contraseña
     */
    public Usuario autenticarUsuario(String nombreUsuario, String password) throws SQLException {
        Usuario usuario = obtenerUsuarioPorNombre(nombreUsuario);
        if (usuario != null && usuario.getPassword().equals(password) && usuario.isActivo()) {
            return usuario;
        }
        return null;
    }

    public List<Usuario> obtenerTodosLosUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nombre_usuario";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(construirUsuario(rs));
            }
        }
        return usuarios;
    }

    public List<Usuario> obtenerUsuariosPorRol(int idRol) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE id_rol = ? ORDER BY nombre_usuario";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idRol);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(construirUsuario(rs));
                }
            }
        }
        return usuarios;
    }

    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre_usuario = ?, password = ?, id_rol = ?, " +
                "activo = ? WHERE id_usuario = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setInt(3, usuario.getIdRol());
            pstmt.setBoolean(4, usuario.isActivo());
            pstmt.setInt(5, usuario.getIdUsuario());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean existeUsuarioPorNombre(String nombreUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) as cantidad FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad") > 0;
                }
            }
        }
        return false;
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre_usuario"),
                rs.getString("password"),
                rs.getInt("id_rol"),
                rs.getBoolean("activo"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime()
        );
    }
}
