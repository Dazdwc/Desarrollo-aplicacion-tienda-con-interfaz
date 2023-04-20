package ElEscuadronDeLasConsultas.Modelo;

import java.sql.SQLException;

public interface ArticuloDAO {

     void crearArticulo(Articulo articulo) throws SQLException;
     void mostrarArticulo() throws SQLException;
     Articulo recogerArticulo(String codigoArticulo) throws SQLException ;
     boolean existeArticulo(String codigo) throws SQLException;

}
