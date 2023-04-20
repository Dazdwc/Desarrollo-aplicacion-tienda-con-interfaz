package ElEscuadronDeLasConsultas.Controlador;
import ElEscuadronDeLasConsultas.Modelo.*;

import java.sql.*;
public class ControladorDAO {

    private final Conexion conexion = new Conexion();

    //Funciones que aseguren la integridad
    //Articulo
    public boolean existeArticuloDAO(String codigo) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.conectar();
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

    //Cliente
    public boolean existeClienteDAO(String mail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.conectar();
            String query = "SELECT COUNT(*) FROM cliente WHERE mail = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, mail);

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

    //Pedido
    public boolean existePedidoDAO(int numeroPedido) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.conectar();
            String query = "SELECT COUNT(*) FROM pedido WHERE numeroPedido = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, numeroPedido);

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

//Create de los distintas clases (cliente, Articulo, Pedido):
    public void crearClienteDao(Cliente cliente) throws SQLException {
        Connection connection = conexion.conectar();
        String query = "INSERT INTO cliente (mail,nombre,nif,domicilio,premium,calcAnual,descuentoEnv) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cliente.getMail());
        statement.setString(2, cliente.getNombre());
        statement.setString(3, cliente.getNif());
        statement.setString(4, cliente.getDomicilio());
        statement.setString(5, cliente.tipoCliente());
        statement.setDouble(7, cliente.calcAnual());
        statement.setDouble(6, cliente.descuentoEnv());


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

    public void crearArticuloDao(Articulo articulo) throws SQLException {
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

    public void crearPedidoDao(Pedido pedido) throws SQLException {
        Connection connection = conexion.conectar();
        String query = "INSERT INTO pedido (numeroPedido,cantidad,fecha,Articulo,Cliente) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, pedido.getNumeroPedido());
        statement.setInt(2, pedido.getCantidad());
        statement.setString(3, pedido.getFechaHora());
        statement.setString(4, pedido.getArticulo().getCodigoArticulo());
        statement.setString(5, pedido.getCliente().getMail());

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

}

//Consultas a la base (Cliente, Articulo, Pedido)

