package org.example.residentialvehiclemanager.model;

import java.time.LocalDateTime;

/**
 * Modelo para Residentes
 */
public class Residente {
    private int idResidente;
    private String nombresCompletos;
    private String telefono;
    private String correoElectronico;
    private String tipoVinculo; // Propietario, Arrendatario, Familiar, Otro
    private int idInmueble;
    private LocalDateTime fechaCreacion;

    public Residente(int idResidente, String nombresCompletos, String telefono,
                     String correoElectronico, String tipoVinculo, int idInmueble,
                     LocalDateTime fechaCreacion) {
        this.idResidente = idResidente;
        this.nombresCompletos = nombresCompletos;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.tipoVinculo = tipoVinculo;
        this.idInmueble = idInmueble;
        this.fechaCreacion = fechaCreacion;
    }

    public Residente(String nombresCompletos, String telefono, String correoElectronico,
                     String tipoVinculo, int idInmueble) {
        this.nombresCompletos = nombresCompletos;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.tipoVinculo = tipoVinculo;
        this.idInmueble = idInmueble;
    }

    // Getters y Setters
    public int getIdResidente() { return idResidente; }
    public void setIdResidente(int idResidente) { this.idResidente = idResidente; }

    public String getNombresCompletos() { return nombresCompletos; }
    public void setNombresCompletos(String nombresCompletos) { this.nombresCompletos = nombresCompletos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTipoVinculo() { return tipoVinculo; }
    public void setTipoVinculo(String tipoVinculo) { this.tipoVinculo = tipoVinculo; }

    public int getIdInmueble() { return idInmueble; }
    public void setIdInmueble(int idInmueble) { this.idInmueble = idInmueble; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public String toString() {
        return nombresCompletos + " (" + tipoVinculo + ")";
    }
}
