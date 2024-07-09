package org.example.proyecto.Tables;

public class Cliente {
    private String nombre; // 00009123 Nombre del cliente
    private String apellido;// 00009123 Apellido del cliente
    private String direccion;// 00009123 Dirreccion del cliente
    private String telefono;// 00009123 Telefono del cliente
    private int idCliente;
    public Cliente(int idCliente,String nombre, String apellido, String direccion, String telefono) {// 00009123 Construimos el cliente con nombre, apellido, direccion y telefono
        this.idCliente=idCliente;// 00009123 Inicializamos el id cliente
        this.nombre = nombre;// 00009123 Inicializamos el nombre
        this.apellido = apellido;// 00009123 Inicializamos el apellido
        this.direccion = direccion;// 00009123 Inicializamos la direccion
        this.telefono = telefono;// 00009123 Inicializamos el numero de telefono
    }
    public int getIdCliente() {// 00009123 Retornamos el id
        return idCliente;
    }

    public void setIdCliente(int idCliente) {// 00009123 Seteamos el id
        this.idCliente = idCliente;
    }

    public String getNombre() {// 00009123 Retornamos el nombre
        return nombre;
    }

    public void setNombre(String nombre) {// 00009123 Seteamos el nombre
        this.nombre = nombre;
    }

    public String getApellido() {// 00009123 Retornamos el apellido
        return apellido;
    }

    public void setApellido(String apellido) {// 00009123 Seteamos el apellido
        this.apellido = apellido;
    }

    public String getDireccion() {// 00009123 Retornamos la direccion del cliente
        return direccion;
    }

    public void setDireccion(String direccion) {// 00009123 Seteamos la direccion del cliente
        this.direccion = direccion;
    }

    public String getTelefono() {// 00009123 Retornamos el telefono del cliente
        return telefono;
    }

    public void setTelefono(String telefono) {// 00009123 Seteamos el telefono del cliente
        this.telefono = telefono;
    }
}
