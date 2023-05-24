package com.example.proyecto_cuatro.Modelo;

public interface FactoryDAO {
    ArticuloDAO createArticuloDAO();
    ClienteDAO createClienteDAO();
    PedidoDAO createPedidoDAO();
}