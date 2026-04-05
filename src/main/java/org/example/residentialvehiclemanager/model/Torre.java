package org.example.residentialvehiclemanager.model;

import java.time.LocalDateTime;

/**
 * Modelo para Torres
 */
public class Torre {
    private int idTorre;
    private String nombreTorre;
    private LocalDateTime fechaCreacion;

    public Torre(int idTorre, String nombreTorre, LocalDateTime fechaCreacion) {
        this.idTorre = idTorre;
        this.nombreTorre = nombreTorre;
        this.fechaCreacion = fechaCreacion;
    }

    public Torre(String nombreTorre) {
        this.nombreTorre = nombreTorre;
    }

    // Getters y Setters
    public int getIdTorre() { return idTorre; }
    public void setIdTorre(int idTorre) { this.idTorre = idTorre; }

    public String getNombreTorre() { return nombreTorre; }
    public void setNombreTorre(String nombreTorre) { this.nombreTorre = nombreTorre; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public String toString() {
        return nombreTorre;
    }
}
