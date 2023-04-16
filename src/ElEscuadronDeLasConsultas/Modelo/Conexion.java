package ElEscuadronDeLasConsultas.Modelo;

import java.sql.*;
public class Conexion {

    private final String url = "jdbc:mysql://localhost:3306/elescuadrondelasconsultas";
    private final String user = "root";
    private final String password = "Mm6260923";

    private Connection connection = null;

    public Connection conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, this.user, password);
        }
        return connection;
    }

    public void cerrar() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}