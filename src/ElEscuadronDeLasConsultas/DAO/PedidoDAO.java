package ElEscuadronDeLasConsultas.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ElEscuadronDeLasConsultas.Modelo.Articulo;
import ElEscuadronDeLasConsultas.Modelo.Cliente;
import ElEscuadronDeLasConsultas.Modelo.Conexion;
import ElEscuadronDeLasConsultas.Modelo.Pedido;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;


public class PedidoDAO {
    private final Conexion conexion = new Conexion();

    //Metodo para consultar si existe el pedido
    public boolean existePedidoDAO(int numeroPedido) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Conectando a la base de datos
            connection = conexion.conectar();
            // Se crea la consulta SQL parametrizada
            String query = "SELECT COUNT(*) FROM pedido WHERE numeroPedido = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, numeroPedido);

            // Deshabilito la confirmación automática de la transacción
            connection.setAutoCommit(false);

            // Ejecuto la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Obtenemos el número de filas del resultado
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            // Confirmamos la transacción
            connection.commit();

            // Devolver true si se encontró al menos una fila, de lo contrario false
            return rowCount > 0;

        } catch (SQLException e) {
            // En caso de una excepción, deshace la transacción
            connection.rollback();
            throw e;

        } finally {
            //Habilitar la confirmación automática de la transacción y cerrar la conexión
            if (connection != null) {
                connection.setAutoCommit(true);
                conexion.cerrar();
            }
        }
    }

    //Metodo para crear el pedido
    public void crearPedidoDAO(Pedido pedido) throws SQLException {

        // Se establece la conexión con la base de datos
        Connection connection = conexion.conectar();

        // Definimos la consulta SQL para insertar un nuevo pedido en la tabla "pedido"
        String query = "INSERT INTO pedido (numeroPedido, cantidad, fecha, articulo, cliente) VALUES (?, ?, ?, ?, ?)";

        // Creamos un objeto PreparedStatement a partir de la consulta SQL
        PreparedStatement statement = connection.prepareStatement(query);

        // Establecemos los valores de los parámetros de la consulta SQL a partir de los datos del pedido
        statement.setInt(1, pedido.getNumeroPedido());
        statement.setInt(2, pedido.getCantidad());

        // Convertir la fecha y hora del pedido de String a un objeto Timestamp que se puede almacenar en MySQL
        String fechaHoraString = pedido.getFechaHora(); // obtiene la fecha y hora en formato String
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")); // crea un objeto LocalDateTime a partir del String
        Timestamp fechaHoraTimestamp = Timestamp.valueOf(fechaHora); // convierte el objeto LocalDateTime a un objeto Timestamp que se puede almacenar en MySQL
        statement.setTimestamp(3, fechaHoraTimestamp);

        // Establecemos el código del artículo y el correo electrónico del cliente del pedido
        statement.setString(4, pedido.getArticulo().getCodigoArticulo());
        statement.setString(5, pedido.getCliente().getMail());

        try {
            // Iniciar una transacción
            connection.setAutoCommit(false);

            // Ejecutar la consulta SQL para insertar el pedido en la tabla "pedido"
            statement.executeUpdate();

            // Confirmar la transacción
            connection.commit();

        } catch (SQLException e) {
            // Si ocurre un error, deshacer la transacción
            connection.rollback();
            throw e;
        } finally {
            // Restablecer la conexión a la configuración predeterminada
            connection.setAutoCommit(true);
            // Cerrar la conexión
            conexion.cerrar();
        }
    }

    /*//Metodo para consultar pedidos pendientes
    public void mostrarPedidoPendienteDAO() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT numeroPedido,cantidad,fecha,Articulo,Cliente FROM pedido";
        PreparedStatement statement = connection.prepareStatement(query);
        int contador = 0;
        ResultSet result = null;

        try {
            result = statement.executeQuery();
            while (result.next()) {
                int numeroPedido = result.getInt("numeroPedido");
                int cantidad = result.getInt("cantidad");
                Timestamp fecha = result.getTimestamp("fecha");
                String articuloNombre = result.getString("Articulo");
                String clienteNombre = result.getString("Cliente");

                Cliente cliente = recogerClienteDAO(clienteNombre);
                Articulo articulo = recogerArticuloDAO(articuloNombre);

                LocalDateTime fechaConver = fecha.toLocalDateTime();


                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);
                if (pedido.pedidoEnviado() == false) {
                    System.out.println(pedido.toString());
                }

            }
            if (contador == 0) {
                System.out.println("no existen pedidos que mostrar");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
    }*/

}
