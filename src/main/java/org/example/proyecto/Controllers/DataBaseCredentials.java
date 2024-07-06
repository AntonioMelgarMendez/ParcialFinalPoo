package org.example.proyecto.Controllers;

public class DataBaseCredentials {

        private static final DataBaseCredentials instance = new DataBaseCredentials();
        private String username;
        private String password;
        private String port;
        private String url;
        private String Database;

        private DataBaseCredentials() {
            this.username = "root";
            this.password = "1234";
            this.port="3306";
            this.url= "jdbc:mysql://localhost:"+port;
            this.Database = "dbSistemaBanco";
        }

    public String getDatabase() {
        return Database;
    }

    public void setDatabase(String database) {
        Database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public static DataBaseCredentials getInstance() {
                return instance;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUsername() {
                return username;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPassword() {
                return password;
            }

}
