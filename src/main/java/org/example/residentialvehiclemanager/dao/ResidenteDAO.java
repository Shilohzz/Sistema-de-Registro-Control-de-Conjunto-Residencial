package org.example.residentialvehiclemanager.dao;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.model.Residente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Residente
 */
public class ResidenteDAO {
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    public int crearResidente(Residente residente) throws SQLException {
        String sql = "INSERT INTO residentes (nombres_completos, telefono, correo_electronico, " +
                "tipo_vinculo, id_inmueble) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, residente.getNombresCompletos());
            pstmt.setString(2, residente.getTelefono());
            pstmt.setString(3, residente.getCorreoElectronico());
            pstmt.setString(4, residente.getTipoVinculo());
            pstmt.setInt(5, residente.getIdInmueble());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo crear el residente");
    }

    public Residente obtenerResidentePorId(int id) throws SQLException {
        String sql = "SELECT * FROM residentes WHERE id_residente = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirResidente(rs);
                }
            }
        }
        return null;
    }

    public List<Residente> obtenerResidentesPorInmueble(int idInmueble) throws SQLException {
        List<Residente> residentes = new ArrayList<>();
        String sql = "SELECT * FROM residentes WHERE id_inmueble = ? ORDER BY nombres_completos";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idInmueble);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    residentes.add(construirResidente(rs));
                }
            }
        }
        return residentes;
    }

    public List<Residente> obtenerTodosLosResidentes() throws SQLException {
        List<Residente> residentes = new ArrayList<>();
        String sql = "SELECT * FROM residentes ORDER BY nombres_completos";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                residentes.add(construirResidente(rs));
            }
        }
        return residentes;
    }

    public boolean actualizarResidente(Residente residente) throws SQLException {
        String sql = "UPDATE residentes SET nombres_completos = ?, telefono = ?, " +
                "correo_electronico = ?, tipo_vinculo = ? WHERE id_residente = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, residente.getNombresCompletos());
            pstmt.setString(2, residente.getTelefono());
            pstmt.setString(3, residente.getCorreoElectronico());
            pstmt.setString(4, residente.getTipoVinculo());
            pstmt.setInt(5, residente.getIdResidente());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarResidente(int idResidente) throws SQLException {
        String sql = "DELETE FROM residentes WHERE id_residente = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idResidente);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Residente construirResidente(ResultSet rs) throws SQLException {
        return new Residente(
                rs.getInt("id_residente"),
                rs.getString("nombres_completos"),
                rs.getString("telefono"),
                rs.getString("correo_electronico"),
                rs.getString("tipo_vinculo"),
                rs.getInt("id_inmueble"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime()
        );
    }
}
