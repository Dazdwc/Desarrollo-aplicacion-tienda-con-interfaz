package com.example.proyecto_cuatro.Modelo;

import java.util.List;

public interface ArticuloDAO {
    void create(Articulo articulo);
    Articulo read(String codigoArticulo);
    void update(Articulo articulo);
    void delete(String codigoArticulo);
    List<Articulo> getAll();
    boolean existeArticulo(String codigoArticulo);
    public Articulo getArticulo(String codigoArticulo);

}