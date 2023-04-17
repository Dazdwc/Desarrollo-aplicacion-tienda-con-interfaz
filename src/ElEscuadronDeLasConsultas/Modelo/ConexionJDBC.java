package ElEscuadronDeLasConsultas.Modelo;

import java.sql.*;
public class ConexionJDBC {
    //Introducimos la url de conexion con el name de nuestra BBDD
    //Luego introducimos los parametros de contraseña y usuario de a BBDD

    private final String url = "jdbc:mysql://localhost:3306/onlinestore";
    private final String user = "root";
    private final String password = "root";
    private Connection connection = null;

    //Conectar la BBDD Si la conexión es nula o esta cerrada, se conectará a través
    //de los parámetros user, pw and url.
    public Connection conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    //A traves del metodo close de la clase connectar verifica si la conexion es nula o no
    // si no lo es y esta abierta, llama a close para cerrarla.
    public void desconectar() throws SQLException {
        if (connection != null && !connection.isClosed()) connection.close();
    }

}