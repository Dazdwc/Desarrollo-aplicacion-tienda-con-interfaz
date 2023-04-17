package ElEscuadronDeLasConsultas.DAO;

import java.sql.SQLException;

import ElEscuadronDeLasConsultas.Modelo.Cliente;
import ElEscuadronDeLasConsultas.Modelo.ClientePremium;
import ElEscuadronDeLasConsultas.Modelo.ClienteStandar;
import ElEscuadronDeLasConsultas.Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDAO implements ClienteFactory{

    private final Conexion conexion = new Conexion();

    //Método para consultar si existe el cliente
    @Override
    public boolean existeClienteDAO(String mail) throws SQLException {
        // Creamos las variables necesarias
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establecemos la conexión a la base de datos
            connection = conexion.conectar();
            // Creamos la consulta SQL
            String query = "SELECT COUNT(*) FROM cliente WHERE mail = ?";
            // Preparamos la sentencia con la consulta SQL
            statement = connection.prepareStatement(query);
            // Establecemos el valor del parámetro de la consulta SQL con el correo electrónico proporcionado
            statement.setString(1, mail);

            // Ejecutamos la consulta SQL y obtenemos los resultados
            resultSet = statement.executeQuery();

            // Obtenemos el número de filas afectadas por la consulta SQL
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            // Devolvemos true si se encontró al menos un cliente con ese correo electrónico, de lo contrario devolvemos false
            return rowCount > 0;

        } catch (SQLException e) {
            // Si ocurre una excepción, la relanzamos para que pueda ser manejada en otro lugar
            throw e;

        } finally {
            // Cerramos los recursos en orden inverso a como se abrieron para evitar excepciones
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            // La conexión se cierra automáticamente al final del bloque try-with-resources
        }
    }

    //Método para crear Cliente
    @Override
    public void crearClienteDao(Cliente cliente) throws SQLException {
        // Establecemos la conexión con la base de datos.
        Connection connection = conexion.conectar();

        // Creamos la consulta para insertar el nuevo cliente.
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
            // Inicia la transacción
            connection.setAutoCommit(false);

            // Ejecuta la operación DML dentro de la transacción
            statement.executeUpdate();

            // Confirma la transacción, si todas las operaciones DML se completaron con éxito
            connection.commit();

        } catch (SQLException e) {
            // Deshace la transacción, si hubo algún error
            connection.rollback();
            throw e;
        } finally {
            // Restablecer el modo de autocommit
            connection.setAutoCommit(true);
            conexion.cerrar();
        }
    }

    //Método para consultar los Clientes
    @Override
    public void mostrarClientesDAO() throws SQLException {
        // Establecer la conexión a la base de datos
        Connection connection = conexion.conectar();

        // Crear la consulta SQL para obtener los datos de los clientes
        String query = "SELECT nombre, nif, domicilio, tipoCliente, mail FROM cliente";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            // Ejecutar la consulta SQL y obtener el resultado
            ResultSet result = statement.executeQuery();

            // Recorrer el resultado fila por fila
            while (result.next()) {
                // Obtener los datos del cliente de la fila actual
                String nombre = result.getString("nombre");
                String nif = result.getString("nif");
                String domicilio = result.getString("domicilio");
                String tipoCliente = result.getString("tipoCliente");
                String mail = result.getString("mail");

                // Crear un objeto Cliente según el tipo de cliente
                Cliente cliente = null;
                if (tipoCliente.equals("Standar")) {
                    cliente = new ClienteStandar(mail, nombre, nif, domicilio);
                } else if (tipoCliente.equals("Premium")) {
                    cliente = new ClientePremium(mail, nombre, nif, domicilio);
                }

                // Mostrar la información del cliente en la pantalla
                if (cliente != null) {
                    System.out.println(cliente.toString());
                }
            }
        } catch (SQLException e) {
            // En caso de error, lanzar la excepción para que se maneje en un nivel superior
            throw e;
        } finally {
            // Cerrar la conexión a la base de datos
            conexion.cerrar();
        }
    }

}
