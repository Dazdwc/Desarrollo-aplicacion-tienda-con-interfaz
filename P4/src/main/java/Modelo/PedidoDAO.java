package Modelo;


import org.hibernate.HibernateException;

import java.util.List;

public interface PedidoDAO {
    void create(Pedido pedido);
    Pedido read(int numeroPedido);
    void update(Pedido pedido);
    void delete(Pedido pedido);
    List<Pedido> getAll();
    public Pedido getPedido(int numeroPedido);
    boolean existePedido(int numeroPedido);
    void mostrarPedidoPendiente();
    void mostrarPedidoEnviado();
    void mostrarPedidoEnviadoFiltrado(String correo);
    void mostrarPedidoPendienteFiltrado(String correo);

}