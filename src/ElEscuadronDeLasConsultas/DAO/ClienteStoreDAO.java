package ElEscuadronDeLasConsultas.DAO;
import ElEscuadronDeLasConsultas.Modelo.Cliente;
import ElEscuadronDeLasConsultas.Modelo.ClientePremium;
import ElEscuadronDeLasConsultas.Modelo.ClienteStandar;

 public class ClienteStoreDAO {

    public Cliente crearCliente(String mail, String nombre, String nif, String domicilio, String tipoCliente) {
        if (tipoCliente.equals("Standar")) {
            return new ClienteStandar(mail, nombre, nif, domicilio);
        } else if (tipoCliente.equals("Premium")) {
            return new ClientePremium(mail, nombre, nif, domicilio);
        }
        return null;
    }

}
