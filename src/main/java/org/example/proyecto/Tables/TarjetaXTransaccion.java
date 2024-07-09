package org.example.proyecto.Tables;

import java.sql.Date;

public class TarjetaXTransaccion {
    private int idCliente; // 00083823: Define el ID del cliente asociado a la transacción
    private int idTransaccion; // 00083823: Define el ID único de la transacción
    private String facilitador; // 00083823: Almacena el nombre del facilitador de la transacción
    private int CantidadCompras; // 00083823: Almacena la cantidad de compras realizadas en la transacción
    private double totalMonto; // 00083823: Almacena el monto total gastado en la transacción

    // 00083823: Constructor que inicializa todos los campos de la transacción
    public TarjetaXTransaccion(int idCliente, int idTransaccion, String facilitador, int cantidadCompras, double totalMonto) {
        this.idCliente = idCliente;
        this.idTransaccion = idTransaccion;
        this.facilitador = facilitador;
        CantidadCompras = cantidadCompras;
        this.totalMonto = totalMonto;
    }

    // 00083823: Método getter para obtener el ID del cliente
    public int getIdCliente() {
        return idCliente;
    }

    // 00083823: Método setter para establecer el ID del cliente
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // 00083823: Método getter para obtener el ID de la transacción
    public int getIdTransaccion() {
        return idTransaccion;
    }

    // 00083823: Método setter para establecer el ID de la transacción
    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    // 00083823: Método getter para obtener el facilitador de la transacción
    public String getFacilitador() {
        return facilitador;
    }

    // 00083823: Método setter para establecer el facilitador de la transacción
    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    // 00083823: Método getter para obtener la cantidad de compras en la transacción
    public int getCantidadCompras() {
        return CantidadCompras;
    }

    // 00083823: Método setter para establecer la cantidad de compras en la transacción
    public void setCantidadCompras(int cantidadCompras) {
        CantidadCompras = cantidadCompras;
    }

    // 00083823: Método getter para obtener el monto total gastado en la transacción
    public double getTotalMonto() {
        return totalMonto;
    }

    // 00083823: Método setter para establecer el monto total gastado en la transacción
    public void setTotalMonto(double totalMonto) {
        this.totalMonto = totalMonto;
    }
}
