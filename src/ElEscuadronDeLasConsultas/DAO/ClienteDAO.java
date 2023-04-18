package ElEscuadronDeLasConsultas.DAO;
import ElEscuadronDeLasConsultas.Modelo.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ClienteDAO {

    private final Conexion conexion = new Conexion();
    Scanner teclado = new Scanner(System.in);


    //Función mejorada con bloque try-with-resources para manejar conexion. PreparedStatement y ResultSet
    //Evitará tener que cerrar explicitamente el bloque finally y reduce la posibilidad de errores al hacerlo
    //manuealmente
    public boolean existeClienteDAO(String mail) throws SQLException {
        boolean existeCliente = false;

        String query = "SELECT COUNT(*) FROM cliente WHERE mail = ?";

        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mail);

            // Iniciar transacción
            connection.setAutoCommit(false);

            // Ejecutar la consulta SQL dentro de la transacción
            try (ResultSet resultSet = statement.executeQuery()) {
                // Obtener el número de filas afectadas por la consulta SQL
                resultSet.next();
                int rowCount = resultSet.getInt(1);

                existeCliente = rowCount > 0;

                // Confirmar la transacción
                connection.commit();
            } catch (SQLException e) {
                // Deshacer la transacción si hubo algún error
                connection.rollback();
                throw e;
            }
        }
        return existeCliente;
    }


    //Se ha utilizado un bloque try-with-resources para asegurarme que los objetos Connection y Prepared Statement
    // se cierren correctamente despues de usarlo. Por lo tanto he quitado el bloque finally que había.
    public void crearClienteDao(Cliente cliente) throws SQLException {
        try (Connection connection = conexion.conectar()) {
            String query = "INSERT INTO cliente (mail, nombre, nif, domicilio, tipoCliente, calcAnual, descuentoEnv) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, cliente.getMail());
                statement.setString(2, cliente.getNombre());
                statement.setString(3, cliente.getNif());
                statement.setString(4, cliente.getDomicilio());
                statement.setString(5, cliente.tipoCliente());
                statement.setDouble(7, cliente.calcAnual());
                statement.setDouble(6, cliente.descuentoEnv());

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
            }
        }
    }


//Consultas a la base (Cliente, Articulo, Pedido)

    public Cliente recogerClienteDAO(String mail) throws SQLException {
        Connection connection = conexion.conectar();
        Cliente cliente = null;
        String query = "SELECT mail,nombre, nif, domicilio, tipoCliente FROM cliente WHERE mail=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, mail);

        try {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String nombre = result.getString("nombre");
                String nif = result.getString("nif");
                String domicilio = result.getString("domicilio");
                String tipoCliente = result.getString("tipoCliente");

                // Utilizar ClienteFactory para crear el objeto de tipo Cliente
                ClienteStoreDAO clienteFactory = new ClienteStoreDAO();
                cliente = clienteFactory.crearCliente(mail, nombre, nif, domicilio, tipoCliente);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar(); // Cerrar la conexión en el finally
        }
        return cliente;
    }

    //Devuelve un mapa donde la clave es el correo electrónico del cliente y el valor es el objeto cliente.

    public Map<String, Cliente> obtenerClientesDAO() throws SQLException {
        Map<String, Cliente> clientes = new HashMap<>();

        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT mail, nombre, nif, domicilio, tipoCliente FROM cliente");
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                String mail = result.getString("mail");
                String nombre = result.getString("nombre");
                String nif = result.getString("nif");
                String domicilio = result.getString("domicilio");
                String tipoCliente = result.getString("tipoCliente");

                Cliente cliente;
                if (tipoCliente.equals("Standard")) {
                    cliente = new ClienteStandar(mail, nombre, nif, domicilio);
                } else if (tipoCliente.equals("Premium")) {
                    cliente = new ClientePremium(mail, nombre, nif, domicilio);
                } else {
                    throw new IllegalArgumentException("Tipo de cliente no válido: " + tipoCliente);
                }

                clientes.put(mail, cliente);
            }
        }
        return clientes;
    }

    //Se ha hecho una mejorar para que no se haga la verificación dentro dfel bucle y mejore el rendimiento.
    public void mostrarStandarDAO() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT nombre, nif, domicilio, tipoCliente, mail FROM cliente WHERE tipoCliente = 'Standar'";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String nombre = result.getString("nombre");
                String nif = result.getString("nif");
                String domicilio = result.getString("domicilio");
                String tipoCliente = result.getString("tipoCliente");
                String mail = result.getString("mail");

                // Construir un objeto Cliente
                Cliente cliente = new ClienteStandar(mail, nombre, nif, domicilio);

                // Mostrar la información del cliente en la pantalla
                System.out.println(cliente.toString());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
    }

    //Mostrar Clientes Premium
    public void mostrarPremiumDAO() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT nombre, nif, domicilio, tipoCliente, mail FROM cliente";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String tipoCliente = result.getString("tipoCliente");

                // Filtrar solo los clientes Premium
                if (tipoCliente.equals("Premium")) {
                    String nombre = result.getString("nombre");
                    String nif = result.getString("nif");
                    String domicilio = result.getString("domicilio");
                    String mail = result.getString("mail");

                    // Construir un objeto Cliente Premium
                    Cliente cliente = new ClientePremium(mail, nombre, nif, domicilio);

                    // Mostrar la información del cliente en la pantalla
                    System.out.println(cliente.toString());
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
    }
}