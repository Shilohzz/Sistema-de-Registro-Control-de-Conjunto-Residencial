package org.example.residentialvehiclemanager.model;

import java.time.LocalDateTime;

/**
 * Modelo para Registros de Acceso
 */
public class RegistroAcceso {
    private int idRegistro;
    private LocalDateTime fechaHoraIngreso;
    private String placaVehiculo;
    private int idUsuarioPorteria;
    private String observaciones;

    public RegistroAcceso(int idRegistro, LocalDateTime fechaHoraIngreso,
                          String placaVehiculo, int idUsuarioPorteria, String observaciones) {
        this.idRegistro = idRegistro;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.placaVehiculo = placaVehiculo;
        this.idUsuarioPorteria = idUsuarioPorteria;
        this.observaciones = observaciones;
    }

    public RegistroAcceso(String placaVehiculo, int idUsuarioPorteria, String observaciones) {
        this.placaVehiculo = placaVehiculo;
        this.idUsuarioPorteria = idUsuarioPorteria;
        this.observaciones = observaciones;
        this.fechaHoraIngreso = LocalDateTime.now();
    }

    // Getters y Setters
    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public LocalDateTime getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public int getIdUsuarioPorteria() {
        return idUsuarioPorteria;
    }

    public void setIdUsuarioPorteria(int idUsuarioPorteria) {
        this.idUsuarioPorteria = idUsuarioPorteria;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return fechaHoraIngreso + " - " + placaVehiculo;
    }
}