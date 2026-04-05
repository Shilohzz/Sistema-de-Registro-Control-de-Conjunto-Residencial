package org.example.residentialvehiclemanager.model;

import java.time.LocalDateTime;

/**
 * Modelo para Roles de Usuario
 */
public class Rol {
    private int idRol;
    private String nombreRol;
    private String descripcion;
    private LocalDateTime fechaCreacion;

    public Rol(int idRol, String nombreRol, String descripcion, LocalDateTime fechaCreacion) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public Rol(String nombreRol, String descripcion) {
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return nombreRol;
    }
}
