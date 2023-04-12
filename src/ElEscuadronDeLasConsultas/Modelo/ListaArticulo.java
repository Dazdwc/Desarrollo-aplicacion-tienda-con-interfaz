package ElEscuadronDeLasConsultas.Modelo;

public class ListaArticulo<T extends Articulo> extends Lista<T> {
    public Articulo obtenerCodigoArticulo(String codigoArticulo) {
        for (T articulo : lista) {
            if (articulo.getCodigoArticulo().equals(codigoArticulo)) {
                return articulo;
            }
        }
        return null;
    }

    public boolean existeArticulo(String codigo) {
        for (T articulo : lista) {
            if (articulo.getCodigoArticulo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

}
