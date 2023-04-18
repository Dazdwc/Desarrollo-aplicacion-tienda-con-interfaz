package ElEscuadronDeLasConsultas.Modelo;

import java.sql.*;
import java.time.LocalDateTime;

public interface PedidoDAO {
    boolean existePedido(int numeroPedido) throws SQLException;
    void crearPedido(Pedido pedido) throws SQLException;
    void eliminarPedido(int numPedido) throws SQLException;
    void mostrarPedidoPendiente() throws SQLException;
    void mostrarPedidoEnviado() throws SQLException;
    void mostrarPedidoEnviadoFiltrado(String correo) throws SQLException;
    void mostrarPedidoPendienteFiltrado(String correo) throws SQLException;

}
