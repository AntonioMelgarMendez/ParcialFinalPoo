package org.example.proyecto.Controllers.Tables;

import java.sql.Date;
import java.time.temporal.TemporalAccessor;

public class Transaccion {

    private int idTransaccion; // 00038623 Define un campo privado para almacenar el ID de la transacción
    private Date fechaCompra; // 00038623 Define un campo privado para almacenar la fecha de compra
    private double totalMonto; // 00038623 Define un campo privado para almacenar el monto total de la transacción
    private String descripcion; // 00038623 Define un campo privado para almacenar la descripción de la transacción
    private int idCliente; // 00038623 Define un campo privado para almacenar el ID del cliente asociado a la transacción

    public Transaccion(int idTransaccion, Date fechaCompra, double totalMonto, String descripcion, int idCliente) { // 00038623 Constructor de la clase Transaccion
        this.idTransaccion = idTransaccion; // 00038623 Inicializa el campo idTransaccion con el valor proporcionado
        this.fechaCompra = fechaCompra; // 00038623 Inicializa el campo fechaCompra con el valor proporcionado
        this.totalMonto = totalMonto; // 00038623 Inicializa el campo totalMonto con el valor proporcionado
        this.descripcion = descripcion; // 00038623 Inicializa el campo descripcion con el valor proporcionado
        this.idCliente = idCliente; // 00038623 Inicializa el campo idCliente con el valor proporcionado
    }

    // Getters and Setters
    public int getIdTransaccion() { // 00038623 Getter para el campo idTransaccion (lo mismo para los demas getters)
        return idTransaccion; // 00038623 Retorna el valor de idTransaccion (lo mismo para los demas setters)
    }

    public void setIdTransaccion(int idTransaccion) { // 00038623 Setter para el campo idTransaccion
        this.idTransaccion = idTransaccion; // 00038623 Asigna el valor proporcionado a idTransaccion
    }

    public Date getFechaCompra() { // 00038623 Getter para el campo fechaCompra
        return fechaCompra; // 00038623 Retorna el valor de fechaCompra
    }

    public void setFechaCompra(Date fechaCompra) { // 00038623 Setter para el campo fechaCompra
        this.fechaCompra = fechaCompra; // 00038623 Asigna el valor proporcionado a fechaCompra
    }

    public double getTotalMonto() { // 00038623 Getter para el campo totalMonto
        return totalMonto; // 00038623 Retorna el valor de totalMonto
    }

    public void setTotalMonto(double totalMonto) { // 00038623 Setter para el campo totalMonto
        this.totalMonto = totalMonto; // 00038623 Asigna el valor proporcionado a totalMonto
    }

    public String getDescripcion() { // 00038623 Getter para el campo descripcion
        return descripcion; // 00038623 Retorna el valor de descripcion
    }

    public void setDescripcion(String descripcion) { // 00038623 Setter para el campo descripcion
        this.descripcion = descripcion; // 00038623 Asigna el valor proporcionado a descripcion
    }

    public int getIdCliente() { // 00038623 Getter para el campo idCliente
        return idCliente; // 00038623 Retorna el valor de idCliente
    }

    public void setIdCliente(int idCliente) { // 00038623 Setter para el campo idCliente
        this.idCliente = idCliente; // 00038623 Asigna el valor proporcionado a idCliente
    }
}
