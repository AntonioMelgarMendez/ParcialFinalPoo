package org.example.proyecto.Controllers;

import java.sql.Date;

public class Transaccion {

    private int idTransaccion;
    private Date fechaCompra;
    private double totalMonto;
    private String descripcion;
    private int idCliente;

    public Transaccion(int idTransaccion, Date fechaCompra, double totalMonto, String descripcion, int idCliente) {
        this.idTransaccion = idTransaccion;
        this.fechaCompra = fechaCompra;
        this.totalMonto = totalMonto;
        this.descripcion = descripcion;
        this.idCliente = idCliente;
    }

    // Getters and Setters
    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotalMonto() {
        return totalMonto;
    }

    public void setTotalMonto(double totalMonto) {
        this.totalMonto = totalMonto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
