package ElEscuadronDeLasConsultas.Modelo;

import java.sql.*;
public class Conexion {

    private final String url = "jdbc:mysql://localhost:3306/onlinestore";
    private final String user = "root";
    private final String password = "1234";

    private Connection connection = null;

    public Connection conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public void cerrar() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}