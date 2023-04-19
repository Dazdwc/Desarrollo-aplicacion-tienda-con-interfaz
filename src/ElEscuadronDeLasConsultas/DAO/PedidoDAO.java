package ElEscuadronDeLasConsultas.DAO;

import ElEscuadronDeLasConsultas.DAO.ClienteDAO;
import ElEscuadronDeLasConsultas.Modelo.*;
import ElEscuadronDeLasConsultas.DAO.ArticuloDAO;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;



public class PedidoDAO  {

    private final Conexion conexion = new Conexion();

    public boolean existePedidoDAO(int numeroPedido) throws SQLException {
        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM pedido WHERE numeroPedido = ?")) {
            statement.setInt(1, numeroPedido);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int rowCount = resultSet.getInt(1);
                    return rowCount > 0;
                }
            }
        }
        return false;
    }


    //Tenemos que recoger el cliente y el articulo para la funcionalidad del pedido.
    public Cliente recogerClienteDAO(String mail) throws SQLException {
        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT nombre, nif, domicilio,tipoCliente FROM cliente WHERE mail = ?")) {
            statement.setString(1, mail);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String nombre = result.getString("nombre");
                    String nif = result.getString("nif");
                    String domicilio = result.getString("domicilio");
                    String tipoCliente = result.getString("tipoCliente");
                    if (tipoCliente.equals("Standar")) {
                        return new ClienteStandar(mail, nombre, nif, domicilio);
                    } else if (tipoCliente.equals("Premium")) {
                        return new ClientePremium(mail, nombre, nif, domicilio);
                    }
                }
            }
            return null;
        }
    }

    //Recogemos el articulo
    public Articulo recogerArticuloDAO(String codigoArticulo) throws SQLException {
        Articulo articulo = null;

        // Utilizamos el bloque try-with-resources para asegurarnos de que los recursos se cierren correctamente
        try (Connection connection = conexion.conectar();
             PreparedStatement statement = crearStatement(connection, codigoArticulo);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                String codigo = result.getString("codigoArticulo");
                String descripcion = result.getString("descripcion");
                float precio = result.getFloat("pvp");
                double gastosEnvio = result.getDouble("gastosEnvio");
                int preparacion = result.getInt("preparacionMin");
                articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);
            } else {
                throw new SQLException("No se encontró ningún artículo con el código: " + codigoArticulo);
            }

        } catch (SQLException e) {
            throw new SQLException("Error al recoger el artículo con el código: " + codigoArticulo, e);
        }

        return articulo;
    }

    // Creamos un método privado para factorizar la creación del objeto PreparedStatement
    private PreparedStatement crearStatement(Connection connection, String codigoArticulo) throws SQLException {
        String query = "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo WHERE codigoArticulo=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, codigoArticulo);
        return statement;
    }


    public void eliminarPedido(int numeroPedido) throws SQLException{

        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM pedido WHERE numeroPedido=?")) {
            statement.setInt(1, numeroPedido);
            statement.executeQuery();
        }catch(Exception e){
            System.out.println("Algo no ha ido bien eliminando el pedido");
            System.out.println(e);
        }
    }

    public void crearPedidoDAO(Pedido pedido) throws SQLException {

        // Se establece la conexión con la base de datos
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conexion.conectar();

            // Definimos la consulta SQL para insertar un nuevo pedido en la tabla "pedido"
            String query = "INSERT INTO pedido (numeroPedido, cantidad, fecha, articulo, cliente) VALUES (?, ?, ?, ?, ?)";

            // Creamos un objeto PreparedStatement a partir de la consulta SQL
            statement = connection.prepareStatement(query);

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
            // Cerrar el PreparedStatement y la conexión
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                conexion.cerrar();
            }
        }
    }
    public void mostrarPedidoPendienteDAO() throws SQLException {
        String query = "SELECT p.numeroPedido, p.cantidad, p.fecha, a.codigoArticulo, c.mail " +
                "FROM pedido p " +
                "INNER JOIN articulo a ON p.articulo = a.nombre " +
                "INNER JOIN cliente c ON p.cliente = c.nombre " +
                "WHERE NOT EXISTS (SELECT 1 FROM envio e WHERE e.numeroPedido = p.numeroPedido)";

        try (Connection connection = conexion.conectar();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {

            int contador = 0;
            while (result.next()) {
                int numeroPedido = result.getInt("numeroPedido");
                int cantidad = result.getInt("cantidad");
                Timestamp fecha = result.getTimestamp("fecha");
                String codigoArticulo = result.getString("codigoArticulo");
                String mailCliente = result.getString("mail");

                Cliente cliente = recogerClienteDAO(mailCliente);
                Articulo articulo = recogerArticuloDAO(codigoArticulo);
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                Pedido pedido = new Pedido(numeroPedido, cantidad, articulo, cliente);
                System.out.println(pedido.toString());
                contador++;
            }
            if (contador == 0) {
                System.out.println("no existen pedidos pendientes que mostrar");
            }
        } catch (SQLException e) {
            throw e;
        }
    }




}
