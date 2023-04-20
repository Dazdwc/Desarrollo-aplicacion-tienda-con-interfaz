package ElEscuadronDeLasConsultas.Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticuloDAOimp implements ArticuloDAO  {
    private final Conexion conexion = new Conexion();

    @Override
    public void crearArticulo(Articulo articulo) throws SQLException{
        Connection connection = conexion.conectar();
        String query = "INSERT INTO articulo (codigoArticulo,descripcion,pvp,gastosEnvio,preparacionMin) VALUES (?,?,?,?,?)";
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
            // Deshacer la transacción si hubo algún error
            connection.rollback();
            throw e;
        } finally {
            // Restablecer el modo de autocommit
            connection.setAutoCommit(true);
            conexion.cerrar();
        }

    }

    @Override
    public void mostrarArticulo() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String codigo = result.getString("codigoArticulo");
                String descripcion = result.getString("descripcion");
                float precio = result.getFloat("pvp");
                double gastosEnvio = result.getDouble("gastosEnvio");
                int preparacion = result.getInt("preparacionMin");

                // Construir un objeto Articulo
                Articulo articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);

                // Mostrar la información del cliente en la pantalla
                System.out.println(articulo);

            }
        } finally {
            conexion.cerrar();
        }
    }

    @Override
    public Articulo recogerArticulo(String codigoArticulo) throws SQLException {
        Connection connection = conexion.conectar();
        Articulo articulo = null;
        String query = "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo WHERE codigoArticulo=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, codigoArticulo);

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            String codigo = result.getString("codigoArticulo");
            String descripcion = result.getString("descripcion");
            float precio = result.getFloat("pvp");
            double gastosEnvio = result.getDouble("gastosEnvio");
            int preparacion = result.getInt("preparacionMin");
            // Construir un objeto Articulo
            articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);
        }
        return articulo;
    }

    @Override
    public boolean existeArticulo(String codigo) throws SQLException {
        Connection connection = conexion.conectar();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            String query = "SELECT COUNT(*) FROM articulo WHERE codigoArticulo = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, codigo);

            // Iniciar transacción
            connection.setAutoCommit(false);

            // Ejecutar la consulta SQL dentro de la transacción
            resultSet = statement.executeQuery();

            // Obtener el número de filas afectadas por la consulta SQL
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            // Confirmar la transacción
            connection.commit();

            return rowCount > 0;

        } catch (SQLException e) {
            // Deshacer la transacción si hubo algún error
            connection.rollback();
            throw e;

        } finally {
            // Restablecer el modo de autocommit y cerrar los recursos
            if (connection != null) {
                connection.setAutoCommit(true);
                conexion.cerrar();
            }
        }
    }
}
