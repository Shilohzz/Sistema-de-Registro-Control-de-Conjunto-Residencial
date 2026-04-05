package org.example.residentialvehiclemanager.controller;

import org.example.residentialvehiclemanager.dao.UsuarioDAO;
import org.example.residentialvehiclemanager.model.Usuario;

import java.sql.SQLException;

/**
 * Controlador de Autenticación
 * Gestiona las sesiones y autenticación de usuarios
 */
public class AuthenticationController {
    private static AuthenticationController instance;
    private Usuario usuarioActual;
    private final UsuarioDAO usuarioDAO;

    private AuthenticationController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public static AuthenticationController getInstance() {
        if (instance == null) {
            instance = new AuthenticationController();
        }
        return instance;
    }

    /**
     * Autentica un usuario con credenciales
     */
    public boolean autenticar(String nombreUsuario, String password) {
        try {
            usuarioActual = usuarioDAO.autenticarUsuario(nombreUsuario, password);
            return usuarioActual != null;
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        usuarioActual = null;
    }

    /**
     * Obtiene el usuario actual
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Verifica si existe una sesión activa
     */
    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    /**
     * Verifica si el usuario actual tiene un rol específico
     */
    public boolean tieneRol(String nombreRol) {
        if (usuarioActual == null) {
            return false;
        }
        // Los IDs de roles son: 1=Administrador, 2=Portero, 3=Residente
        return nombreRol.equalsIgnoreCase("Administrador") && usuarioActual.getIdRol() == 1 ||
                nombreRol.equalsIgnoreCase("Portero") && usuarioActual.getIdRol() == 2 ||
                nombreRol.equalsIgnoreCase("Residente") && usuarioActual.getIdRol() == 3;
    }

    /**
     * Verifica si el usuario es administrador
     */
    public boolean esAdministrador() {
        return usuarioActual != null && usuarioActual.getIdRol() == 1;
    }

    /**
     * Verifica si el usuario es portero
     */
    public boolean esPortero() {
        return usuarioActual != null && usuarioActual.getIdRol() == 2;
    }
}