package ElEscuadronDeLasConsultas.DAO;

import ElEscuadronDeLasConsultas.Modelo.*;
import ElEscuadronDeLasConsultas.DAO.*;
import java.sql.*;
import java.time.LocalDateTime;

public class PedidoDAO {
    private final Conexion conexion = new Conexion();
    public boolean existePedidoDAO(int numeroPedido) throws SQLException {
        String query = "SELECT COUNT(*) FROM pedido WHERE numeroPedido = ?";

        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, numeroPedido);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            return rowCount > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al buscar el pedido", e);
        }
    }

    public boolean eliminarPedidoDAO(int numPedido) throws SQLException {
        Connection connection = conexion.conectar();
        boolean eliminado = false;
        String query = "DELETE FROM pedido WHERE numeroPedido=? AND enviado=0";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, numPedido);

        try {
            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                eliminado = true;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
        return eliminado;
    }

    

}
