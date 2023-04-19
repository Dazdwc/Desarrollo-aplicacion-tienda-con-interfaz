package ElEscuadronDeLasConsultas.DAO;
import ElEscuadronDeLasConsultas.Modelo.Articulo;
import ElEscuadronDeLasConsultas.Modelo.Cliente;
import ElEscuadronDeLasConsultas.Modelo.Pedido;

import java.sql.SQLException;

public interface PedidoStoreDAO {

    boolean existePedidoDAO(int numeroPedido) throws SQLException;

    Cliente recogerClienteDAO(String mail) throws SQLException;

    Articulo recogerArticuloDAO(String codigoArticulo) throws SQLException;

    void eliminarPedido(int numeroPedido) throws SQLException;

    void crearPedidoDAO(Pedido pedido) throws SQLException;

    void mostrarPedidoPendienteDAO() throws SQLException;

    void mostrarPedidoEnviadoDAO() throws SQLException;
}
