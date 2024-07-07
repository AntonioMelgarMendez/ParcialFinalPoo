package org.example.proyecto.Tables;

public class Tarjeta { // 00018523 Clase asociada a la tabla "tarjeta" de la base de datos
    private String numTarjeta; // 00018523 Número de la tarjeta
    private String facilitador; // 00018523 Nombre del facilitador de la tarjeta
    private String tipoTarjeta; // 00018523  Tipo de tarjeta (Crédito (C) o Débito (D))
    private int idCliente; // 00018523 ID del cliente asociado a la tarjeta

    public Tarjeta(String numTarjeta, String facilitador, String tipoTarjeta, int idCliente) { // 00018523 Constructor de la tarjeta, toma en cuenta el número, facilitador, tipo e id del cliente
        this.numTarjeta = numTarjeta; // 00018523 Inicializa el campo numTarjeta con el valor proporcionado
        this.facilitador = facilitador; // 00018523 Inicializa el campo facilitador con el valor proporcionado
        this.tipoTarjeta = tipoTarjeta; // 00018523 Inicializa el campo tipoTarjeta con el valor proporcionado
        this.idCliente = idCliente; // 00018523 Inicializa el campo idCliente con el valor proporcionado
    }

    public String getNumTarjeta() { // 00018523 Método que retorna el número de la tarjeta
        return numTarjeta; // 00018523 Retorna el número de la tarjeta
    }

    public void setNumTarjeta(String numTarjeta) { // 00018523 Asigna el número de la tarjeta al valor proporcionado
        this.numTarjeta = numTarjeta; // 00018523 Asigna el número de la tarjeta a el campo "numTarjeta" de la clase
    }

    public String getFacilitador() { // 00018523 Método que retorna el facilitador de la tarjeta
        return facilitador; // 00018523 Retorna el facilitador de la tarjeta
    }

    public void setFacilitador(String facilitador) { // 00018523 Asigna el facilitador al valor proporcionado
        this.facilitador = facilitador; // 00018523 Asigna el facilitador al campo "facilitador" de la clase
    }

    public String getTipoTarjeta() { // 00018523 Método que retorna el tipo de la tarjeta
        return tipoTarjeta; // 00018523 Retorna el tipo de la tarjeta
    }

    public void setTipoTarjeta(String tipoTarjeta) { // 00018523 Asigna el tipo de la tarjeta al valor proporcionado
        this.tipoTarjeta = tipoTarjeta; // 00018523 Asigna el tipo de la tarjeta al campo "tipoTarjeta" de la clase
    }

    public int getIdCliente() { // 00018523 Método que retorna el ID del cliente asociado a la tarjeta
        return idCliente; // 00018523 Retorna el ID del cliente asociado a la tarjeta
    }

    public void setIdCliente(int idCliente) { // 00018523 Asigna el ID del cliente al valor proporcionado
        this.idCliente = idCliente; // 00018523 Asigna el ID del cliente al campo "idCliente" de la clase
    }
}
