package ElEscuadronDeLasConsultas.Modelo;

import java.sql.SQLException;

public interface ClienteDAO {

    boolean existeCliente(String mail) throws SQLException;
    void crearCliente(Cliente cliente) throws SQLException;
    Cliente recogerCliente(String mail) throws SQLException;
    void mostrarClientes() throws SQLException;
    void mostrarStandar() throws SQLException;
    void mostrarPremium() throws SQLException;


}
