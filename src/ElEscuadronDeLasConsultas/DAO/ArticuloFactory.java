package ElEscuadronDeLasConsultas.DAO;

import ElEscuadronDeLasConsultas.Modelo.Articulo;

import java.sql.SQLException;

public interface ArticuloFactory {


    boolean existeArticuloDao(String codigo) throws SQLException;

    //Método para crear Articulo
    void crearArticuloDao(Articulo articulo) throws SQLException;

    //Método para mostrar articulos
    void mostrarArticuloDao() throws SQLException;
}
