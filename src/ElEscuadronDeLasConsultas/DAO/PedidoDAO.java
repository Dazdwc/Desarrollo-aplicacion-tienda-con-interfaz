package ElEscuadronDeLasConsultas.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ElEscuadronDeLasConsultas.Modelo.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;


public class PedidoDAO implements PedidoInterface{
    private final Conexion conexion = new Conexion();

    //Metodo para consultar si existe el pedido
    @Override
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
    @Override
    public Cliente recogerClienteDAO(String mail) throws SQLException {
        // Conexión a la base de datos
        try (Connection connection = conexion.conectar();
             // Preparación de la consulta
             PreparedStatement statement = connection.prepareStatement("SELECT nombre, nif, domicilio, tipoCliente FROM cliente WHERE mail=?")) {
            // Asignación del parámetro de la consulta
            statement.setString(1, mail);
            // Ejecución de la consulta y recogida de los resultados
            try (ResultSet result = statement.executeQuery()) {
                // Si hay resultados, se crea un nuevo objeto Cliente y se devuelve
                if (result.next()) {
                    String nombre = result.getString("nombre");
                    String nif = result.getString("nif");
                    String domicilio = result.getString("domicilio");
                    String tipoCliente = result.getString("tipoCliente");
                    // Si el tipo de cliente es "Standar", se crea un objeto ClienteStandar
                    if (tipoCliente.equals("Standar")) {
                        return new ClienteStandar(mail, nombre, nif, domicilio);
                        // Si el tipo de cliente es "Premium", se crea un objeto ClientePremium
                    } else if (tipoCliente.equals("Premium")) {
                        return new ClientePremium(mail, nombre, nif, domicilio);
                    }
                }
            }
            // Si no se encuentra ningún cliente con ese correo electrónico, se devuelve null
            return null;
        }
    }

    @Override
    public Articulo recogerArticuloDAO(String codigoArticulo) throws SQLException {
        Connection connection = null; // Inicializamos la variable connection a null
        PreparedStatement statement = null; // Inicializamos la variable statement a null
        ResultSet result = null; // Inicializamos la variable result a null
        Articulo articulo = null; // Inicializamos la variable articulo a null

        try {
            connection = conexion.conectar(); // Establecemos conexión a la base de datos
            String query = "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo WHERE codigoArticulo=?";
            statement = connection.prepareStatement(query); // Creamos un objeto PreparedStatement a partir de la consulta
            statement.setString(1, codigoArticulo); // Asignamos el valor de codigoArticulo al primer parámetro de la consulta
            result = statement.executeQuery(); // Ejecutamos la consulta

            if (result.next()) { // Si la consulta devuelve al menos un resultado
                String codigo = result.getString("codigoArticulo"); // Obtenemos el valor del campo codigoArticulo de la consulta
                String descripcion = result.getString("descripcion"); // Obtenemos el valor del campo descripcion de la consulta
                float precio = result.getFloat("pvp"); // Obtenemos el valor del campo pvp de la consulta
                double gastosEnvio = result.getDouble("gastosEnvio"); // Obtenemos el valor del campo gastosEnvio de la consulta
                int preparacion = result.getInt("preparacionMin"); // Obtenemos el valor del campo preparacionMin de la consulta
                articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion); // Creamos un objeto Articulo a partir de los datos obtenidos de la consulta
            }

        } catch (SQLException e) { // Si se produce una excepción durante la ejecución de la consulta
            throw e; // Lanzamos la excepción hacia el método que llama a este método

        } finally { // En cualquier caso, aunque se produzca una excepción o no
            try {
                if (result != null) { // Si la variable result no es null
                    result.close(); // Cerramos el objeto ResultSet
                }
                if (statement != null) { // Si la variable statement no es null
                    statement.close(); // Cerramos el objeto PreparedStatement
                }
                if (connection != null) { // Si la variable connection no es null
                    connection.close(); // Cerramos la conexión a la base de datos
                }

            } catch (SQLException e) { // Si se produce una excepción durante el cierre de alguno de los objetos
                throw e; // Lanzamos la excepción hacia el método que llama a este método
            }
        }
        return articulo; // Devolvemos el objeto Articulo obtenido de la consulta, o null si no se ha encontrado ningún resultado
    }


    @Override
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



    //Metodo para crear el pedido
    @Override
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

    //Metodo para consultar pedidos pendientes
    @Override
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


                Pedido pedido = new Pedido(numeroPedido, cantidad,articulo, cliente, fechaConver);
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
    }

    @Override
    public void mostrarPedidoEnviadoDAO() throws SQLException {
        Connection connection = conexion.conectar(); // Se establece la conexión con la base de datos
        String query = "SELECT numeroPedido,cantidad,fecha,Articulo,Cliente FROM pedido"; // Se crea una consulta SQL para seleccionar los pedidos de la tabla "pedido"
        PreparedStatement statement = connection.prepareStatement(query); // Se prepara la consulta
        int contador = 0; // Se inicializa el contador de pedidos enviados a cero
        ResultSet result = null; // Se inicializa el resultado a null

        try {
            result = statement.executeQuery(); // Se ejecuta la consulta y se guarda el resultado en la variable "result"
            while (result.next()) { // Mientras haya resultados en el resultado de la consulta...
                int numeroPedido = result.getInt("numeroPedido"); // Se obtiene el número de pedido del resultado
                int cantidad = result.getInt("cantidad"); // Se obtiene la cantidad del resultado
                Timestamp fecha = result.getTimestamp("fecha"); // Se obtiene la fecha del resultado
                String articuloNombre = result.getString("Articulo"); // Se obtiene el nombre del artículo del resultado
                String clienteNombre = result.getString("Cliente"); // Se obtiene el nombre del cliente del resultado

                Cliente cliente = recogerClienteDAO(clienteNombre); // Se obtiene el cliente a partir del nombre del cliente
                Articulo articulo = recogerArticuloDAO(articuloNombre); // Se obtiene el artículo a partir del nombre del artículo

                LocalDateTime fechaConver = fecha.toLocalDateTime(); // Se convierte la fecha obtenida en un objeto LocalDateTime

                // Se crea un objeto "Pedido" a partir de los datos obtenidos
                Pedido pedido = new Pedido(numeroPedido, cantidad, articulo, cliente, fechaConver);
                if (pedido.pedidoEnviado() == true) { // Si el pedido ha sido enviado...
                    System.out.println(pedido.toString()); // Se muestra el pedido por pantalla
                    contador = contador + 1; // Se incrementa el contador de pedidos enviados
                }
            }
            if (contador == 0) { // Si no se ha encontrado ningún pedido enviado...
                System.out.println("No hay pedidos disponibles");
            }
        } catch (SQLException e) { // Si se produce una excepción de SQL...
            throw e; // Se lanza la excepción
        } finally {
            conexion.cerrar(); // Se cierra la conexión con la base de datos
        }
    }

}
