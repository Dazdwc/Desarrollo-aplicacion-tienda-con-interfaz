package ElEscuadronDeLasConsultas.Modelo;

public class Datos {
    private ListaArticulo listaArticulo;
    private ListaCliente listaCliente;
    private ListaPedido listaPedido;

    public Datos(){
        listaArticulo = new ListaArticulo();
        listaCliente = new ListaCliente();
        listaPedido = new ListaPedido();

    }

    public ListaArticulo getListaArticulo() {
        return listaArticulo;
    }

    public void setListaArticulo(ListaArticulo listaArticulo) {
        this.listaArticulo = listaArticulo;
    }

    public ListaCliente getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(ListaCliente listaCliente) {
        this.listaCliente = listaCliente;
    }

    public ListaPedido getListaPedido() {
        return listaPedido;
    }

    public void setListaPedido(ListaPedido listaPedido) {
        this.listaPedido = listaPedido;
    }
}
