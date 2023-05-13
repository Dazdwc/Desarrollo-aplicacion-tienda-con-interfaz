package Modelo;

public interface FactoryDAO {
    ArticuloDAO createArticuloDAO();
    ClienteDAO createClienteDAO();
    PedidoDAO createPedidoDAO();
}