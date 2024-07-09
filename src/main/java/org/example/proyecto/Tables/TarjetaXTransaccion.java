package org.example.proyecto.Tables;

import java.sql.Date;

public class TarjetaXTransaccion {
    private int idCliente; // 00083823: Define el ID del cliente asociado a la transacción
    private int CantidadCompras; // 00083823: Almacena la cantidad de compras realizadas en la transacción
    private double totalMonto; // 00083823: Almacena el monto total gastado en la transacción

    // 00083823: Constructor que inicializa todos los campos de la transacción
    public TarjetaXTransaccion(int idCliente, int cantidadCompras, double totalMonto) {
        this.idCliente = idCliente;
        this.CantidadCompras = cantidadCompras;
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
