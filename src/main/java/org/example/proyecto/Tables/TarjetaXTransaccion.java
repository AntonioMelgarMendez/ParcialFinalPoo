package org.example.proyecto.Tables;

import java.sql.Date;

public class TarjetaXTransaccion {
    private int idCliente;
    private int idTransaccion;
    private String facilitador;
    private int CantidadCompras;// 00038623 Define un campo privado para almacenar el ID de la transacción
    private double totalMonto; // 00038623 Define un campo privado para almacenar el monto total de la transacción

    public TarjetaXTransaccion(int idCliente, int idTransaccion, String facilitador, int cantidadCompras, double totalMonto) {
        this.idCliente = idCliente;
        this.idTransaccion = idTransaccion;
        this.facilitador = facilitador;
        CantidadCompras = cantidadCompras;
        this.totalMonto = totalMonto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public int getCantidadCompras() {
        return CantidadCompras;
    }

    public void setCantidadCompras(int cantidadCompras) {
        CantidadCompras = cantidadCompras;
    }

    public double getTotalMonto() {
        return totalMonto;
    }

    public void setTotalMonto(double totalMonto) {
        this.totalMonto = totalMonto;
    }
}
