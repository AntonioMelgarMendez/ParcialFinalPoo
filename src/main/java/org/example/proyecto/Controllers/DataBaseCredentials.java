package org.example.proyecto.Controllers;

public class DataBaseCredentials {
    /*00009123 Inicializamos la instancia de las credenciales
    que se usaran para acceder a la base de datos*/
    private static final DataBaseCredentials instance = new DataBaseCredentials();
    /*00009123 Definimos todas los campos para poder establecer la conexion*/
    private String username;
    private String password;
    private String port;
    private String url;
    private String Database;

    /*00009123 Ponemos el constructor de la clase oculto para hacerla singleton
    para evitar duplicidad de credenciales*/
    private DataBaseCredentials() {
        /*00009123 Agregamos valores por defecto al inicializar la clase con el user root,
        el pass y el puerto por defecto*/
        this.username = "root";
        this.password = "1234";
        this.port = "3306";
        /*00009123 Construimos la url con el numero de puerto*/
        this.url = "jdbc:mysql://localhost:" + port;
        /*00009123 Seleccionamos la base de datos que se usara por defecto*/
        this.Database = "dbSistemaBanco";
    }
    /*00009123 Implementando el get para acceder a la base de datos actual*/
    public String getDatabase() {
        return Database;
    }
    /*00009123 Implementando el set para colocar la base de datos a utilizar*/
    public void setDatabase(String database) {
        Database = database;
    }
    /*00009123 Implementando el get para acceder a la url para hacer las queries */
    public String getUrl() {
        return url;
    }
    /*00009123 Implementando el set para colocar la url de la base*/
    public void setUrl(String url) {
        this.url = url;
    }
    /*00009123 Implementando el get para acceder al puerto actual */
    public String getPort() {
        return port;
    }
    /*00009123 Set para colocar un nuevo port como el actual*/
    public void setPort(String port) {
        this.port = port;
    }
    /*00009123 Obtener la instancia actual de la clase para poder acceder a sus metodos*/
    public static DataBaseCredentials getInstance() {
        return instance;
    }
    /*00009123 Set para colocar el usuario actual*/
    public void setUsername(String username) {
        this.username = username;
    }
    /*00009123 Get para obtener el username del usuario actual*/
    public String getUsername() {
        return username;
    }
    /*00009123 Set para poder colocar una password para acceder a la base*/
    public void setPassword(String password) {
        this.password = password;
    }
    /*00009123 Get para obtener la password actual*/
    public String getPassword() {
        return password;
    }

}
