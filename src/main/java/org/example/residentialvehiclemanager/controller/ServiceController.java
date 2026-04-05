package org.example.residentialvehiclemanager.controller;

import org.example.residentialvehiclemanager.dao.*;
import org.example.residentialvehiclemanager.model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador de Servicios Principales
 * Implementa la lógica de negocio central
 */
public class ServiceController {
    private static ServiceController instance;
    private final TorreDAO torreDAO;
    private final InmuebleDAO inmuebleDAO;
    private final ResidenteDAO residenteDAO;
    private final VehiculoDAO vehiculoDAO;
    private final UsuarioDAO usuarioDAO;
    private final RolDAO rolDAO;
    private final RegistroAccesoDAO registroAccesoDAO;

    private ServiceController() {
        this.torreDAO = new TorreDAO();
        this.inmuebleDAO = new InmuebleDAO();
        this.residenteDAO = new ResidenteDAO();
        this.vehiculoDAO = new VehiculoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.rolDAO = new RolDAO();
        this.registroAccesoDAO = new RegistroAccesoDAO();
    }

    public static ServiceController getInstance() {
        if (instance == null) {
            instance = new ServiceController();
        }
        return instance;
    }

    // ========== TORRES ==========
    public int crearTorre(String nombreTorre) throws SQLException {
        return torreDAO.crearTorre(new Torre(nombreTorre));
    }

    public Torre obtenerTorre(int id) throws SQLException {
        return torreDAO.obtenerTorrePorId(id);
    }

    public List<Torre> obtenerTodasLasTorres() throws SQLException {
        return torreDAO.obtenerTodasLasTorres();
    }

    public boolean actualizarTorre(int id, String nombreTorre) throws SQLException {
        Torre torre = torreDAO.obtenerTorrePorId(id);
        if (torre != null) {
            torre.setNombreTorre(nombreTorre);
            return torreDAO.actualizarTorre(torre);
        }
        return false;
    }

    public boolean eliminarTorre(int id) throws SQLException {
        return torreDAO.eliminarTorre(id);
    }

    // ========== INMUEBLES ==========
    public int crearInmueble(String numeroApartamento, int idTorre) throws SQLException {
        return inmuebleDAO.crearInmueble(new Inmueble(numeroApartamento, idTorre));
    }

    public Inmueble obtenerInmueble(int id) throws SQLException {
        return inmuebleDAO.obtenerInmueblePorId(id);
    }

    public List<Inmueble> obtenerInmueblesPorTorre(int idTorre) throws SQLException {
        return inmuebleDAO.obtenerInmueblesPorTorre(idTorre);
    }

    public List<Inmueble> obtenerTodosLosInmuebles() throws SQLException {
        return inmuebleDAO.obtenerTodosLosInmuebles();
    }

    public boolean actualizarInmueble(int id, String numeroApartamento, int idTorre) throws SQLException {
        Inmueble inmueble = inmuebleDAO.obtenerInmueblePorId(id);
        if (inmueble != null) {
            inmueble.setNumeroApartamento(numeroApartamento);
            inmueble.setIdTorre(idTorre);
            return inmuebleDAO.actualizarInmueble(inmueble);
        }
        return false;
    }

    public boolean eliminarInmueble(int id) throws SQLException {
        return inmuebleDAO.eliminarInmueble(id);
    }

    public int obtenerCantidadResidentesPorInmueble(int idInmueble) throws SQLException {
        return inmuebleDAO.obtenerCantidadResidentes(idInmueble);
    }

    public int obtenerCantidadVehiculosPorInmueble(int idInmueble) throws SQLException {
        return inmuebleDAO.obtenerCantidadVehiculos(idInmueble);
    }

    // ========== RESIDENTES ==========
    public int crearResidente(String nombres, String telefono, String correo,
                              String tipoVinculo, int idInmueble) throws SQLException {
        return residenteDAO.crearResidente(
                new Residente(nombres, telefono, correo, tipoVinculo, idInmueble)
        );
    }

    public Residente obtenerResidente(int id) throws SQLException {
        return residenteDAO.obtenerResidentePorId(id);
    }

    public List<Residente> obtenerResidentesPorInmueble(int idInmueble) throws SQLException {
        return residenteDAO.obtenerResidentesPorInmueble(idInmueble);
    }

    public List<Residente> obtenerTodosLosResidentes() throws SQLException {
        return residenteDAO.obtenerTodosLosResidentes();
    }

    public boolean actualizarResidente(int id, String nombres, String telefono,
                                       String correo, String tipoVinculo) throws SQLException {
        Residente residente = residenteDAO.obtenerResidentePorId(id);
        if (residente != null) {
            residente.setNombresCompletos(nombres);
            residente.setTelefono(telefono);
            residente.setCorreoElectronico(correo);
            residente.setTipoVinculo(tipoVinculo);
            return residenteDAO.actualizarResidente(residente);
        }
        return false;
    }

    public boolean eliminarResidente(int id) throws SQLException {
        return residenteDAO.eliminarResidente(id);
    }

    // ========== VEHÍCULOS ==========
    public void crearVehiculo(String placa, String marca, String modelo,
                              String color, int idInmueble) throws SQLException {
        vehiculoDAO.crearVehiculo(new Vehiculo(placa, marca, modelo, color, idInmueble));
    }

    public Vehiculo obtenerVehiculo(String placa) throws SQLException {
        return vehiculoDAO.obtenerVehiculoPorPlaca(placa);
    }

    public List<Vehiculo> obtenerVehiculosPorInmueble(int idInmueble) throws SQLException {
        return vehiculoDAO.obtenerVehiculosPorInmueble(idInmueble);
    }

    public List<Vehiculo> obtenerTodosLosVehiculos() throws SQLException {
        return vehiculoDAO.obtenerTodosLosVehiculos();
    }

    public boolean actualizarVehiculo(String placa, String marca, String modelo,
                                      String color, int idInmueble) throws SQLException {
        Vehiculo vehiculo = vehiculoDAO.obtenerVehiculoPorPlaca(placa);
        if (vehiculo != null) {
            vehiculo.setMarca(marca);
            vehiculo.setModelo(modelo);
            vehiculo.setColor(color);
            vehiculo.setIdInmuebleDestino(idInmueble);
            return vehiculoDAO.actualizarVehiculo(vehiculo);
        }
        return false;
    }

    public boolean eliminarVehiculo(String placa) throws SQLException {
        return vehiculoDAO.eliminarVehiculo(placa);
    }

    /**
     * Consulta crítica: Obtiene información completa de un vehículo y su propietario
     */
    public InformacionVehiculo obtenerInformacionVehiculo(String placa) throws SQLException {
        return vehiculoDAO.obtenerInformacionVehiculoCompleta(placa);
    }

    // ========== USUARIOS ==========
    public int crearUsuario(String nombreUsuario, String password, int idRol) throws SQLException {
        return usuarioDAO.crearUsuario(new Usuario(nombreUsuario, password, idRol));
    }

    public Usuario obtenerUsuario(int id) throws SQLException {
        return usuarioDAO.obtenerUsuarioPorId(id);
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) throws SQLException {
        return usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() throws SQLException {
        return usuarioDAO.obtenerTodosLosUsuarios();
    }

    public List<Usuario> obtenerUsuariosPorRol(int idRol) throws SQLException {
        return usuarioDAO.obtenerUsuariosPorRol(idRol);
    }

    public boolean actualizarUsuario(int id, String nombreUsuario, String password,
                                     int idRol, boolean activo) throws SQLException {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
        if (usuario != null) {
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setPassword(password);
            usuario.setIdRol(idRol);
            usuario.setActivo(activo);
            return usuarioDAO.actualizarUsuario(usuario);
        }
        return false;
    }

    public boolean eliminarUsuario(int id) throws SQLException {
        return usuarioDAO.eliminarUsuario(id);
    }

    public boolean existeUsuarioPorNombre(String nombreUsuario) throws SQLException {
        return usuarioDAO.existeUsuarioPorNombre(nombreUsuario);
    }

    // ========== REGISTROS DE ACCESO ==========
    public int registrarAccesoVehiculo(String placa, int idUsuario,
                                       String observaciones) throws SQLException {
        return registroAccesoDAO.crearRegistroAcceso(
                new RegistroAcceso(placa, idUsuario, observaciones)
        );
    }

    public List<RegistroAcceso> obtenerRegistrosPorVehiculo(String placa) throws SQLException {
        return registroAccesoDAO.obtenerRegistrosPorVehiculo(placa);
    }

    public List<RegistroAcceso> obtenerRegistrosPorUsuario(int idUsuario) throws SQLException {
        return registroAccesoDAO.obtenerRegistrosPorUsuario(idUsuario);
    }

    public List<RegistroAcceso> obtenerRegistrosRecientes(int cantidad) throws SQLException {
        return registroAccesoDAO.obtenerRegistrosRecientes(cantidad);
    }

    public List<RegistroAcceso> obtenerRegistrosPorFecha(LocalDateTime inicio,
                                                         LocalDateTime fin) throws SQLException {
        return registroAccesoDAO.obtenerRegistrosPorFecha(inicio, fin);
    }

    public boolean eliminarRegistro(int id) throws SQLException {
        return registroAccesoDAO.eliminarRegistro(id);
    }

    // ========== ROLES ==========
    public Rol obtenerRol(int id) throws SQLException {
        return rolDAO.obtenerRolPorId(id);
    }

    public List<Rol> obtenerTodosLosRoles() throws SQLException {
        return rolDAO.obtenerTodosLosRoles();
    }

    public Rol obtenerRolPorNombre(String nombreRol) throws SQLException {
        return rolDAO.obtenerRolPorNombre(nombreRol);
    }

    // ========== ESTADÍSTICAS ==========
    public int obtenerCantidadTorres() throws SQLException {
        return obtenerTodasLasTorres().size();
    }

    public int obtenerCantidadInmuebles() throws SQLException {
        return obtenerTodosLosInmuebles().size();
    }

    public int obtenerCantidadResidentes() throws SQLException {
        return obtenerTodosLosResidentes().size();
    }

    public int obtenerCantidadVehiculos() throws SQLException {
        return obtenerTodosLosVehiculos().size();
    }

    public int obtenerCantidadUsuarios() throws SQLException {
        return obtenerTodosLosUsuarios().size();
    }
}
