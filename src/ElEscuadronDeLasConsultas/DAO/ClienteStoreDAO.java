package ElEscuadronDeLasConsultas.DAO;
import ElEscuadronDeLasConsultas.Modelo.Cliente;
import ElEscuadronDeLasConsultas.Modelo.ClientePremium;
import ElEscuadronDeLasConsultas.Modelo.ClienteStandar;

import java.sql.SQLException;


public interface ClienteStoreDAO {
         //Método para consultar si existe el cliente
         boolean existeClienteDAO(String mail) throws SQLException;

         //Método para crear Cliente
         void crearClienteDao(Cliente cliente) throws SQLException;

         //Método para consultar los Clientes
         void mostrarClientesDAO() throws SQLException;

}

