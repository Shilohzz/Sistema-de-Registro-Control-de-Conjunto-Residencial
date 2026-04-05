package org.example.residentialvehiclemanager.dao;

import org.example.residentialvehiclemanager.database.DatabaseManager;
import org.example.residentialvehiclemanager.model.InformacionVehiculo;
import org.example.residentialvehiclemanager.model.Vehiculo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Vehículo
 */
public class VehiculoDAO {
    private final DatabaseManager dbManager = DatabaseManager.getInstance();

    public void crearVehiculo(Vehiculo vehiculo) throws SQLException {
        String sql = "INSERT INTO vehiculos (placa, marca, modelo, color, id_inmueble_destino) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, vehiculo.getPlaca());
            pstmt.setString(2, vehiculo.getMarca());
            pstmt.setString(3, vehiculo.getModelo());
            pstmt.setString(4, vehiculo.getColor());
            pstmt.setInt(5, vehiculo.getIdInmuebleDestino());
            pstmt.executeUpdate();
        }
    }

    public Vehiculo obtenerVehiculoPorPlaca(String placa) throws SQLException {
        String sql = "SELECT * FROM vehiculos WHERE placa = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, placa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return construirVehiculo(rs);
                }
            }
        }
        return null;
    }

    public List<Vehiculo> obtenerVehiculosPorInmueble(int idInmueble) throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE id_inmueble_destino = ? ORDER BY placa";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idInmueble);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    vehiculos.add(construirVehiculo(rs));
                }
            }
        }
        return vehiculos;
    }

    public List<Vehiculo> obtenerTodosLosVehiculos() throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos ORDER BY placa";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vehiculos.add(construirVehiculo(rs));
            }
        }
        return vehiculos;
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) throws SQLException {
        String sql = "UPDATE vehiculos SET marca = ?, modelo = ?, color = ?, " +
                "id_inmueble_destino = ? WHERE placa = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setString(3, vehiculo.getColor());
            pstmt.setInt(4, vehiculo.getIdInmuebleDestino());
            pstmt.setString(5, vehiculo.getPlaca());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarVehiculo(String placa) throws SQLException {
        String sql = "DELETE FROM vehiculos WHERE placa = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, placa);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Consulta optimizada: Obtiene información completa del vehículo y su propietario
     */
    public InformacionVehiculo obtenerInformacionVehiculoCompleta(String placa) throws SQLException {
        String sql = "SELECT v.placa, v.marca, v.modelo, v.color, " +
                "i.numero_apartamento, t.nombre_torre, " +
                "r.nombres_completos, r.telefono, r.correo_electronico " +
                "FROM vehiculos v " +
                "JOIN inmuebles i ON v.id_inmueble_destino = i.id_inmueble " +
                "JOIN torres t ON i.id_torre = t.id_torre " +
                "LEFT JOIN residentes r ON i.id_inmueble = r.id_inmueble AND r.tipo_vinculo = 'Propietario' " +
                "WHERE v.placa = ? LIMIT 1";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, placa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new InformacionVehiculo(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getString("color"),
                            rs.getString("numero_apartamento"),
                            rs.getString("nombre_torre"),
                            rs.getString("nombres_completos"),
                            rs.getString("telefono"),
                            rs.getString("correo_electronico")
                    );
                }
            }
        }
        return null;
    }

    private Vehiculo construirVehiculo(ResultSet rs) throws SQLException {
        return new Vehiculo(
                rs.getString("placa"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("color"),
                rs.getInt("id_inmueble_destino"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime()
        );
    }
}
