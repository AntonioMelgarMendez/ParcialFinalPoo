package org.example.proyecto.Tables;

public class Cliente { // 00083823 Clase que representa a un cliente.
    private int idCliente; // 00083823 Atributo que almacena el ID del cliente.
    private String nombre; // 00083823 Atributo que almacena el nombre del cliente.
    private String apellido; // 00083823 Atributo que almacena el apellido del cliente.
    private String direccion;// 00009123 Dirreccion del cliente
    private String telefono;// 00009123 Telefono del cliente
    public Cliente(int idCliente, String nombre, String apellido, String direccion, String telefono) { // 00083823 Constructor que inicializa todos los atributos de la clase Cliente.
        this.idCliente = idCliente; // 00083823 Inicializa el ID del cliente.
        this.nombre = nombre; // 00083823 Inicializa el nombre del cliente.
        this.apellido = apellido; // 00083823 Inicializa el apellido del cliente.
        this.direccion = direccion;// 00009123 Inicializamos la direccion
        this.telefono = telefono;// 00009123 Inicializamos el numero de telefono
    }

    public int getIdCliente() { // 00083823 Método getter para obtener el ID del cliente.
        return idCliente; // 00083823 Retorna el ID del cliente.
    }

    public void setIdCliente(int idCliente) { // 00083823 Método setter para establecer el ID del cliente.
        this.idCliente = idCliente; // 00083823 Establece el ID del cliente con el valor proporcionado.
    }

    public String getNombre() { // 00083823 Método getter para obtener el nombre del cliente.
        return nombre; // 00083823 Retorna el nombre del cliente.
    }

    public void setNombre(String nombre) { // 00083823 Método setter para establecer el nombre del cliente.
        this.nombre = nombre; // 00083823 Establece el nombre del cliente con el valor proporcionado.
    }

    public String getApellido() { // 00083823 Método getter para obtener el apellido del cliente.
        return apellido; // 00083823 Retorna el apellido del cliente.
    }

    public void setApellido(String apellido) { // 00083823 Método setter para establecer el apellido del cliente.
        this.apellido = apellido; // 00083823 Establece el apellido del cliente con el valor proporcionado.
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

