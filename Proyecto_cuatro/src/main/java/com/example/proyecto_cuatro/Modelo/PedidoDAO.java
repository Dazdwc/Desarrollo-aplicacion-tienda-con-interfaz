package com.example.proyecto_cuatro.Modelo;


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
    public List<Pedido> getPedidosPorEstado(boolean enviado);
    public List<Pedido>mostrarPedidoFiltrado(String correo);
    String deleteIfNotSent(int numeroPedido);
    public List<Pedido> getPedidos();

}