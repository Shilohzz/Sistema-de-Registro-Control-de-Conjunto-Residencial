package org.example.residentialvehiclemanager.dao;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.model.Inmueble;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Inmueble
 */
public class InmuebleDAO {
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    public int crearInmueble(Inmueble inmueble) throws SQLException {
        String sql = "INSERT INTO inmuebles (numero_apartamento, id_torre) VALUES (?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, inmueble.getNumeroApartamento());
            pstmt.setInt(2, inmueble.getIdTorre());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo crear el inmueble");
    }

    public Inmueble obtenerInmueblePorId(int id) throws SQLException {
        String sql = "SELECT * FROM inmuebles WHERE id_inmueble = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Inmueble(
                            rs.getInt("id_inmueble"),
                            rs.getString("numero_apartamento"),
                            rs.getInt("id_torre"),
                            rs.getTimestamp("fecha_creacion").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public List<Inmueble> obtenerInmueblesPorTorre(int idTorre) throws SQLException {
        List<Inmueble> inmuebles = new ArrayList<>();
        String sql = "SELECT * FROM inmuebles WHERE id_torre = ? ORDER BY numero_apartamento ASC";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idTorre);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    inmuebles.add(new Inmueble(
                            rs.getInt("id_inmueble"),
                            rs.getString("numero_apartamento"),
                            rs.getInt("id_torre"),
                            rs.getTimestamp("fecha_creacion").toLocalDateTime()
                    ));
                }
            }
        }
        return inmuebles;
    }

    public List<Inmueble> obtenerTodosLosInmuebles() throws SQLException {
        List<Inmueble> inmuebles = new ArrayList<>();
        String sql = "SELECT * FROM inmuebles ORDER BY id_torre, numero_apartamento";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                inmuebles.add(new Inmueble(
                        rs.getInt("id_inmueble"),
                        rs.getString("numero_apartamento"),
                        rs.getInt("id_torre"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime()
                ));
            }
        }
        return inmuebles;
    }

    public boolean actualizarInmueble(Inmueble inmueble) throws SQLException {
        String sql = "UPDATE inmuebles SET numero_apartamento = ?, id_torre = ? WHERE id_inmueble = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, inmueble.getNumeroApartamento());
            pstmt.setInt(2, inmueble.getIdTorre());
            pstmt.setInt(3, inmueble.getIdInmueble());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarInmueble(int idInmueble) throws SQLException {
        String sql = "DELETE FROM inmuebles WHERE id_inmueble = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idInmueble);
            return pstmt.executeUpdate() > 0;
        }
    }

    public int obtenerCantidadResidentes(int idInmueble) throws SQLException {
        String sql = "SELECT COUNT(*) as cantidad FROM residentes WHERE id_inmueble = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idInmueble);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }
        }
        return 0;
    }

    public int obtenerCantidadVehiculos(int idInmueble) throws SQLException {
        String sql = "SELECT COUNT(*) as cantidad FROM vehiculos WHERE id_inmueble_destino = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idInmueble);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }
        }
        return 0;
    }
}
