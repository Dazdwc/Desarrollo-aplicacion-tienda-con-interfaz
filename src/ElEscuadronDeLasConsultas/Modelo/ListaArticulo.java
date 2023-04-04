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

}

/* public class ListaArticulo {

    private ArrayList<Articulo> listaArticulos;

    public ListaArticulo() {
        listaArticulos = new ArrayList<>();
    }

    public void agregarArticulo(Articulo articulo) {
        listaArticulos.add(articulo);
    }

    public void eliminarArticulo(Articulo articulo) {
        listaArticulos.remove(articulo);
    }

    public int obtenerCantidadArticulo() {
        return listaArticulos.size();
    }

    public Articulo obtenerArticulo(int codigoArticulo) {
        for (Articulo articulo : listaArticulos) {
            if (articulo.getCodigoArticulo() == codigoArticulo) {
                return articulo;
            }
        }
        return null;
    }

    public ArrayList<Articulo> obtenerTodosArticulos() {
        return new ArrayList<>(listaArticulos);
    } */
