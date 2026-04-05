package org.example.residentialvehiclemanager.model;


import java.time.LocalDateTime;

/**
 * Modelo para Vehículos
 */
public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private int idInmuebleDestino;
    private LocalDateTime fechaCreacion;

    public Vehiculo(String placa, String marca, String modelo, String color,
                    int idInmuebleDestino, LocalDateTime fechaCreacion) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.idInmuebleDestino = idInmuebleDestino;
        this.fechaCreacion = fechaCreacion;
    }

    public Vehiculo(String placa, String marca, String modelo, String color,
                    int idInmuebleDestino) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.idInmuebleDestino = idInmuebleDestino;
    }

    // Getters y Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIdInmuebleDestino() {
        return idInmuebleDestino;
    }

    public void setIdInmuebleDestino(int idInmuebleDestino) {
        this.idInmuebleDestino = idInmuebleDestino;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return placa + " - " + marca + " " + modelo;
    }
}
