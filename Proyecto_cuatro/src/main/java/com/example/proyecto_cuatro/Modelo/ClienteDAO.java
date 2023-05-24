package com.example.proyecto_cuatro.Modelo;

import java.util.List;

public interface ClienteDAO {
    void create(Cliente cliente);
    Cliente read(String mail);
    void update(Cliente cliente);
    void delete(String mail);
    List<Cliente> getAll();
    List<ClientePremium> getAllPremium();
    List<ClienteStandar> getAllStandar();
    boolean existeCliente(String mail);

    public Cliente getCliente(String mail);
}