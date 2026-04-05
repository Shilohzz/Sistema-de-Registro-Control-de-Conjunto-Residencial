package org.example.residentialvehiclemanager.model;

import java.time.LocalDateTime;

/**
 * Modelo para Inmuebles (Apartamentos)
 */
public class Inmueble {
    private int idInmueble;
    private String numeroApartamento;
    private int idTorre;
    private LocalDateTime fechaCreacion;

    public Inmueble(int idInmueble, String numeroApartamento, int idTorre, LocalDateTime fechaCreacion) {
        this.idInmueble = idInmueble;
        this.numeroApartamento = numeroApartamento;
        this.idTorre = idTorre;
        this.fechaCreacion = fechaCreacion;
    }

    public Inmueble(String numeroApartamento, int idTorre) {
        this.numeroApartamento = numeroApartamento;
        this.idTorre = idTorre;
    }

    // Getters y Setters
    public int getIdInmueble() { return idInmueble; }
    public void setIdInmueble(int idInmueble) { this.idInmueble = idInmueble; }

    public String getNumeroApartamento() { return numeroApartamento; }
    public void setNumeroApartamento(String numeroApartamento) { this.numeroApartamento = numeroApartamento; }

    public int getIdTorre() { return idTorre; }
    public void setIdTorre(int idTorre) { this.idTorre = idTorre; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public String toString() {
        return "Apt. " + numeroApartamento;
    }
}
