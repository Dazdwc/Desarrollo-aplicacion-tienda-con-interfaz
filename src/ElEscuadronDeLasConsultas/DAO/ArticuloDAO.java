package ElEscuadronDeLasConsultas.DAO;

import ElEscuadronDeLasConsultas.Modelo.Articulo;
import ElEscuadronDeLasConsultas.Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloDAO implements ArticuloFactory {

    //Variable de conexión
    private final Conexion conexion = new Conexion();

    // Método que comprueba si existe un artículo en la base de datos

    @Override
    public boolean existeArticuloDao(String codigo) throws SQLException {

        // Inicialización de las variables de conexión, sentencia SQL y resultado de la consulta
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Conexión a la base de datos
            connection = conexion.conectar();
            // Sentencia SQL para contar los registros que tengan el código de artículo especificado
            String query = "SELECT COUNT(*) FROM articulo WHERE codigoArticulo = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, codigo);

            // Ejecución de la sentencia SQL
            resultSet = statement.executeQuery();

            // Obtener el número de filas afectadas por la consulta SQL
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            // Devuelve true si la consulta devuelve al menos una fila, lo que significa que el artículo existe
            return rowCount > 0;

        } catch (SQLException e) {
            // Lanza una excepción SQLException si ocurre un error en la consulta SQL
            throw e;

        } finally {
            // Cierre de los recursos para liberar memoria
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    //Método para crear Articulo
    @Override
    public void crearArticuloDao(Articulo articulo) throws SQLException {
        // Conectamos a la bd y añadimos articulo mediante INSERT INTO
        Connection connection = conexion.conectar();
        String query = "INSERT INTO articulo (codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, articulo.getCodigoArticulo());
        statement.setString(2, articulo.getDescripcion());
        statement.setFloat(3, articulo.getPvp());
        statement.setDouble(4, articulo.getGastosEnvio());
        statement.setInt(5, articulo.getPreparacionMin());

        try {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Ejecutar la operación DML dentro de la transacción
            statement.executeUpdate();

            // Confirmar la transacción si todas las operaciones DML se completaron con éxito
            connection.commit();

        } catch (SQLException e) {
            // En caso de error, se vuelve hacía atrás
            connection.rollback();
            throw e;
        } finally {
            // Restablecer el modo de autocommit
            connection.setAutoCommit(true);
            conexion.cerrar();
        }
    }

    //Método para mostrar articulos
    @Override
    public String mostrarArticuloDao() throws SQLException {
        // Conectar a la base de datos
        Connection connection = conexion.conectar();

        // Crear la consulta SQL
        String query = "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo";
        PreparedStatement statement = connection.prepareStatement(query);

        StringBuilder listaArticulos = new StringBuilder();

        try {
            // Ejecutar la consulta y obtener los resultados
            ResultSet result = statement.executeQuery();

            // Recorrer los resultados y construir objetos Articulo
            while (result.next()) {
                String codigo = result.getString("codigoArticulo");
                String descripcion = result.getString("descripcion");
                float precio = result.getFloat("pvp");
                double gastosEnvio = result.getDouble("gastosEnvio");
                int preparacion = result.getInt("preparacionMin");
                Articulo articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);

                // Agregar la información del artículo a la lista de artículos
                if (articulo != null) {
                    listaArticulos.append(articulo.toString()).append("\n");
                }
            }

            // Si la lista de artículos está vacía, agregar un mensaje indicando que no hay artículos
            if (listaArticulos.length() == 0) {
                listaArticulos.append("No hay artículos disponibles");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // Cerrar la conexión a la base de datos
            conexion.cerrar();
        }

        return listaArticulos.toString();
    }


}