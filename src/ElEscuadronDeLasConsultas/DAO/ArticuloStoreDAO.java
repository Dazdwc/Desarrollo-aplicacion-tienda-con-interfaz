package ElEscuadronDeLasConsultas.DAO;

import ElEscuadronDeLasConsultas.Modelo.Articulo;

import java.sql.SQLException;

public interface ArticuloStoreDAO {

    boolean existeArticuloDao(String codigo) throws SQLException;

    //Método para crear Articulo
    void crearArticuloDao(Articulo articulo) throws SQLException;

    //Método para mostrar articulos
    String mostrarArticuloDao() throws SQLException;

    //Metodo para reocger articulosDAO
    Articulo recogerArticuloDAO(String codigoArticulo) throws SQLException;


}
