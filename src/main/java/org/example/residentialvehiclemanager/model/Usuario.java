package org.example.residentialvehiclemanager.model;

import java.time.LocalDateTime;

/**
 * Modelo para Usuarios (Porteros/Administradores)
 */
public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String password;
    private int idRol;
    private boolean activo;
    private LocalDateTime fechaCreacion;

    public Usuario(int idUsuario, String nombreUsuario, String password,
                   int idRol, boolean activo, LocalDateTime fechaCreacion) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.idRol = idRol;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario(String nombreUsuario, String password, int idRol) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.idRol = idRol;
        this.activo = true;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return nombreUsuario + (activo ? " (Activo)" : " (Inactivo)");
    }
}
