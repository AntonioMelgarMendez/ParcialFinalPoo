package org.example.proyecto.Utilities;

public class DataBaseCredentials {
    private static final DataBaseCredentials instance = new DataBaseCredentials(); //00009123 Inicializamos la instancia de las credenciales
    private String username; //00009123 Definimos el campo de username
    private String password;//00009123 Definimos el campo de password
    private String port;//00009123 Definimos el campo de puerto
    private String url;//00009123 Definimos la url
    private String Database;//00009123 Definimos la base de datos a utilizar


    private DataBaseCredentials() {    //00009123 Ponemos el constructor de la clase oculto para hacerla singleton
        this.username = "root"; //00009123 Inicializamos el usuario como root
        this.password = "1234"; //00009123 Inicializamos el pass con 1234
        this.port = "3306"; //00009123 Colocamos el puerto por defecto
        this.url = "jdbc:mysql://localhost:" + port;//00009123 Construimos la url con el numero de puerto
        this.Database = "dbSistemaBanco";//00009123 Seleccionamos la base de datos que se usara por defecto
    }
    public String getDatabase() {
        return Database;//00009123 Implementando el get para acceder a la base de datos actual
    }
    public void setDatabase(String database) {
        Database = database;//00009123 Implementando el set para colocar la base de datos a utilizar
    }
    public String getUrl() {
        return url; //00009123 Implementando el get para acceder a la url para hacer las queries
    }
    public void setUrl(String url) {
        this.url = url;  //00009123 Implementando el set para colocar la url de la base
    }
    public String getPort() {
        return port;//00009123 Implementando el get para acceder al puerto actual
    }
    public void setPort(String port) {
        this.port = port;//00009123 Set para colocar un nuevo port como el actual
    }
    public static DataBaseCredentials getInstance() {
        return instance; //00009123 Obtener la instancia actual de la clase para poder acceder a sus metodos
    }
    public void setUsername(String username) {
        this.username = username;  //00009123 Set para colocar el usuario actual
    }
    public String getUsername() {
        return username;//00009123 Get para obtener el username del usuario actual
    }
    public void setPassword(String password) {
        this.password = password; //00009123 Set para poder colocar una password para acceder a la base
    }
    public String getPassword() {
        return password; //00009123 Get para obtener la password actual
    }

}
