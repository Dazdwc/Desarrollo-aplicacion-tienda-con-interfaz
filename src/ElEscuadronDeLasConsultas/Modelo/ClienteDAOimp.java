package ElEscuadronDeLasConsultas.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ClienteDAOimp implements ClienteDAO {
    private final Conexion conexion = new Conexion();

    public boolean existeCliente(String mail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = conexion.conectar();
            String query = "SELECT COUNT(*) FROM cliente WHERE mail = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, mail);

            // Iniciar transacción
            connection.setAutoCommit(false);

            // Ejecutar la consulta SQL dentro de la transacción
            resultSet = statement.executeQuery();

            // Obtener el número de filas afectadas por la consulta SQL
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            // Confirmar la transacción
            connection.commit();

            return rowCount > 0;

        } catch (SQLException e) {
            // Deshacer la transacción si hubo algún error
            assert connection != null;
            connection.rollback();
            throw e;

        } finally {
            // Restablecer el modo de autocommit y cerrar los recursos
            if (connection != null) {
                connection.setAutoCommit(true);
                conexion.cerrar();
            }
        }
    }

    public void crearCliente(Cliente cliente) throws SQLException {
        Connection connection = conexion.conectar();
        String query = "INSERT INTO cliente (mail,nombre,nif,domicilio,tipoCliente,calcAnual,descuentoEnv) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cliente.getMail());
        statement.setString(2, cliente.getNombre());
        statement.setString(3, cliente.getNif());
        statement.setString(4, cliente.getDomicilio());
        statement.setString(5, cliente.tipoCliente());
        statement.setDouble(6, cliente.calcAnual());
        statement.setDouble(7, cliente.descuentoEnv());


        try {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Ejecutar la operación DML dentro de la transacción
            statement.executeUpdate();

            // Confirmar la transacción si todas las operaciones DML se completaron con éxito
            connection.commit();

        } catch (SQLException e) {
            // Deshacer la transacción si hubo algún error
            connection.rollback();
            throw e;
        } finally {
            // Restablecer el modo de autocommit
            connection.setAutoCommit(true);
            conexion.cerrar();
        }
    }
    public Cliente recogerCliente(String mail) throws SQLException {
        Connection connection = conexion.conectar();
        Cliente cliente = null;
        String query = "SELECT mail,nombre, nif, domicilio, tipoCliente FROM cliente WHERE mail=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, mail);

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            String nombre = result.getString("nombre");
            String nif = result.getString("nif");
            String domicilio = result.getString("domicilio");
            String tipoCliente = result.getString("tipoCliente");
            if (tipoCliente.equals("Standar")) {
                cliente = new ClienteStandar(mail, nombre, nif, domicilio);
            } else if (tipoCliente.equals("Premium")) {
                cliente = new ClientePremium(mail, nombre, nif, domicilio);
            }
        }
        return cliente;
    }
    public void mostrarClientes() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT nombre, nif, domicilio, tipoCliente, mail FROM cliente";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String nombre = result.getString("nombre");
                String nif = result.getString("nif");
                String domicilio = result.getString("domicilio");
                String tipoCliente = result.getString("tipoCliente");
                String mail = result.getString("mail");

                // Construir un objeto Cliente según el tipo de cliente
                Cliente cliente = null;
                if (tipoCliente.equals("Standar")) {
                    cliente = new ClienteStandar(mail, nombre, nif, domicilio);
                } else if (tipoCliente.equals("Premium")) {
                    cliente = new ClientePremium(mail, nombre, nif, domicilio);
                }

                // Mostrar la información del cliente en la pantalla
                if (cliente != null) {
                    System.out.println(cliente);
                }

            }
        } finally {
            conexion.cerrar();
        }
    }
    public void mostrarStandar() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT nombre, nif, domicilio, tipoCliente, mail FROM cliente";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String nombre = result.getString("nombre");
                String nif = result.getString("nif");
                String domicilio = result.getString("domicilio");
                String tipoCliente = result.getString("tipoCliente");
                String mail = result.getString("mail");

                // Construir un objeto Cliente según el tipo de cliente
                Cliente cliente;
                if (tipoCliente.equals("Standar")) {
                    cliente = new ClienteStandar(mail, nombre, nif, domicilio);
                } else {
                    cliente = null;
                }

                // Mostrar la información del cliente en la pantalla
                if (cliente != null) {
                    System.out.println(cliente);
                }

            }
        } finally {
            conexion.cerrar();
        }
    }

    public void mostrarPremium() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT nombre, nif, domicilio, tipoCliente, mail FROM cliente";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String nombre = result.getString("nombre");
                String nif = result.getString("nif");
                String domicilio = result.getString("domicilio");
                String tipoCliente = result.getString("tipoCliente");
                String mail = result.getString("mail");

                // Construir un objeto Cliente según el tipo de cliente
                Cliente cliente;
                if (tipoCliente.equals("Premium")) {
                    cliente = new ClientePremium(mail, nombre, nif, domicilio);
                } else {
                    cliente = null;
                }

                // Mostrar la información del cliente en la pantalla
                if (cliente != null) {
                    System.out.println(cliente);
                }

            }
        } finally {
            conexion.cerrar();
        }
    }
}
