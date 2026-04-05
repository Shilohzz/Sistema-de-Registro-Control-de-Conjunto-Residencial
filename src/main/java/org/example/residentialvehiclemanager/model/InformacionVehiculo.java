package org.example.residentialvehiclemanager.model;

/**
 * Clase DTO para consultas complejas: Información del propietario de un vehículo
 */
public class InformacionVehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String numeroApartamento;
    private String nombreTorre;
    private String propietarioNombre;
    private String propietarioTelefono;
    private String propietarioCorreo;

    public InformacionVehiculo(String placa, String marca, String modelo, String color,
                               String numeroApartamento, String nombreTorre,
                               String propietarioNombre, String propietarioTelefono,
                               String propietarioCorreo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.numeroApartamento = numeroApartamento;
        this.nombreTorre = nombreTorre;
        this.propietarioNombre = propietarioNombre;
        this.propietarioTelefono = propietarioTelefono;
        this.propietarioCorreo = propietarioCorreo;
    }

    // Getters
    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public String getNumeroApartamento() {
        return numeroApartamento;
    }

    public String getNombreTorre() {
        return nombreTorre;
    }

    public String getPropietarioNombre() {
        return propietarioNombre;
    }

    public String getPropietarioTelefono() {
        return propietarioTelefono;
    }

    public String getPropietarioCorreo() {
        return propietarioCorreo;
    }

    @Override
    public String toString() {
        return "Vehículo: " + placa + "\nPropietario: " + propietarioNombre +
                "\nApartamento: " + numeroApartamento + " Torre " + nombreTorre;
    }
}
