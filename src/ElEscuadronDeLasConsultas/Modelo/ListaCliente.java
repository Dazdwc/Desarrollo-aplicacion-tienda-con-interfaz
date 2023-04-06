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