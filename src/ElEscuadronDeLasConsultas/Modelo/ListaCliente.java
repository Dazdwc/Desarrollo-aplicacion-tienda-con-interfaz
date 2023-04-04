package ElEscuadronDeLasConsultas.Modelo;


public class ListaCliente<T extends Cliente> extends Lista<T> {

    public Cliente obtenerClientePorMail(String mail) {
        for (T cliente : lista) {
            if (cliente.getMail().equals(mail)) {
                return cliente;
            }
        }
        return null;
    }

}
/*public class ListaCliente {
    private ArrayList<Cliente> listaClientes;

    public ListaCliente() {
        listaClientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public void eliminarCliente(Cliente cliente) {
        listaClientes.remove(cliente);
    }

    public int obtenerCantidadClientes() {
        return listaClientes.size();
    }

    public Cliente obtenerClientePorMail(String mail) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getMail().equals(mail)) {
                return cliente;
            }
        }
        return null;
    }

    public ArrayList<Cliente> obtenerTodosClientes() {
        return new ArrayList<>(listaClientes);
    } */

