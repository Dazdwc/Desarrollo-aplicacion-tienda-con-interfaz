package ElEscuadronDeLasConsultas.DAO;

import ElEscuadronDeLasConsultas.Modelo.Articulo;
import ElEscuadronDeLasConsultas.Modelo.Cliente;
import ElEscuadronDeLasConsultas.Modelo.Pedido;

import java.sql.SQLException;

public interface PedidoInterface {

    //Metodo para consultar si existe el pedido
    boolean existePedidoDAO(int numeroPedido) throws SQLException;

    Cliente recogerClienteDAO(String mail) throws SQLException;

    Articulo recogerArticuloDAO(String codigoArticulo) throws SQLException;

    void eliminarPedido(int numeroPedido) throws SQLException;

    //Metodo para crear el pedido
    void crearPedidoDAO(Pedido pedido) throws SQLException;

    //Metodo para consultar pedidos pendientes
    void mostrarPedidoPendienteDAO() throws SQLException;

    void mostrarPedidoEnviadoDAO() throws SQLException;
}
