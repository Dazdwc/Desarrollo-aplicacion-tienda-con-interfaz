package ElEscuadronDeLasConsultas.Modelo;


public class ListaPedido<T extends Pedido> extends Lista<T> {
    public Pedido obtenerPorNumPedido(int numeroPedido) {
        for (T pedido : lista) {
            if (pedido.getNumeroPedido() == numeroPedido) {
                return pedido;
            }
        }
        return null;
    }

}