package ElEscuadronDeLasConsultas.Modelo;

public class ListaArticulo<T extends Articulo> extends Lista<T> {
    public Articulo obtenerCodigoArticulo(int codigoArticulo) {
        for (T articulo : lista) {
            if (articulo.getCodigoArticulo() == codigoArticulo) {
                return articulo;
            }
        }
        return null;
    }

    public boolean existeArticulo(int codigo) {
        for (T articulo : lista) {
            if (articulo.getCodigoArticulo() == codigo) {
                return true;
            }
        }
        return false;
    }

}
