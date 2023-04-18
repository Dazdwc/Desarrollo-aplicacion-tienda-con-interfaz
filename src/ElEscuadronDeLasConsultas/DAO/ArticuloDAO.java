package ElEscuadronDeLasConsultas.DAO;
import ElEscuadronDeLasConsultas.Modelo.*;
import ElEscuadronDeLasConsultas.Modelo.Conexion;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ArticuloDAO {
    private final Conexion conexion = new Conexion();

    public boolean existeArticuloDAO(String codigo) throws SQLException {
        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT 1 FROM articulo WHERE codigoArticulo = ?");
        ) {
            statement.setString(1, codigo);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.first();
            }
        } catch (SQLException e) {
            throw new SQLException("Error al buscar el artículo", e);
        }
    }

    //
    public void crearArticuloDao(Articulo articulo) throws SQLException {
        String query = "INSERT INTO articulo (codigoArticulo,descripcion,pvp,gastosEnvio,preparacionMin) VALUES (?,?,?,?,?)";

        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, articulo.getCodigoArticulo());
            statement.setObject(2, articulo.getDescripcion());
            statement.setObject(3, articulo.getPvp());
            statement.setObject(4, articulo.getGastosEnvio());
            statement.setObject(5, articulo.getPreparacionMin());

            // Iniciar transacción
            connection.setAutoCommit(false);

            // Ejecutar la operación DML dentro de la transacción
            statement.executeUpdate();

            // Confirmar la transacción si todas las operaciones DML se completaron con éxito
            connection.commit();
        } catch (SQLException e) {
            // Deshacer la transacción si hubo algún error
            throw e;
        }
    }


    public Articulo recogerArticuloDAO(String codigoArticulo) throws SQLException {
        Articulo articulo = null;

        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo WHERE codigoArticulo=?"
             );
        ) {
            if (codigoArticulo == null) {
                statement.setNull(1, java.sql.Types.VARCHAR);
            } else {
                statement.setString(1, codigoArticulo);
            }

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String codigo = result.getString("codigoArticulo");
                    String descripcion = result.getString("descripcion");
                    float precio = result.getFloat("pvp");
                    double gastosEnvio = result.getDouble("gastosEnvio");
                    int preparacion = result.getInt("preparacionMin");

                    // Construir un objeto Articulo
                    articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return articulo;
    }


    //Te da los artiuclos a traves del arrayList List<Articulos>
    public List<Articulo> listarArticulosDAO() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
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
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
        return articulos;
    }



}
