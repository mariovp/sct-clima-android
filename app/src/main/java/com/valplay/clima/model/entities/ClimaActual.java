package com.valplay.clima.model.entities;

public class ClimaActual {

    private String descripcion;
    private String nombreIcono;
    private int temperaturaActual;
    private int humedad;

    public ClimaActual(String descripcion, String nombreIcono, int temperaturaActual, int humedad) {
        this.descripcion = descripcion;
        this.nombreIcono = nombreIcono;
        this.temperaturaActual = temperaturaActual;
        this.humedad = humedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreIcono() {
        return nombreIcono;
    }

    public int getTemperaturaActual() {
        return temperaturaActual;
    }

    public int getHumedad() {
        return humedad;
    }

}
