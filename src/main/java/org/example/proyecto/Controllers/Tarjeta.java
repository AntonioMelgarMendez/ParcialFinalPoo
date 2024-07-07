package org.example.proyecto.Controllers;

public class Tarjeta {
    private String numTarjeta;
    private String facilitador;
    private String tipoTarjeta;
    private int idCliente;

    public Tarjeta(String numTarjeta, String facilitador, String tipoTarjeta, int idCliente) {
        this.numTarjeta = numTarjeta;
        this.facilitador = facilitador;
        this.tipoTarjeta = tipoTarjeta;
        this.idCliente = idCliente;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
