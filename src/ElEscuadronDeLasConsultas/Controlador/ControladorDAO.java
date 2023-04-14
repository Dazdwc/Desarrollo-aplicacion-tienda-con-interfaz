package ElEscuadronDeLasConsultas.Controlador;
import ElEscuadronDeLasConsultas.Modelo.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class ControladorDAO {

    private final Conexion conexion = new Conexion();
    Scanner teclado = new Scanner(System.in);

    //Funciones que aseguren la integridad
    //Articulo
    public boolean existeArticuloDAO(String codigo) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.conectar();
            String query = "SELECT COUNT(*) FROM articulo WHERE codigoArticulo = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, codigo);

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

    //Cliente
    public boolean existeClienteDAO(String mail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

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

    //Pedido
    public boolean existePedidoDAO(int numeroPedido) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.conectar();
            String query = "SELECT COUNT(*) FROM pedido WHERE numeroPedido = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, numeroPedido);

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

    //Create de los distintas clases (cliente, Articulo, Pedido):
    public void crearClienteDao(Cliente cliente) throws SQLException {
        Connection connection = conexion.conectar();
        String query = "INSERT INTO cliente (mail,nombre,nif,domicilio,tipoCliente,calcAnual,descuentoEnv) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cliente.getMail());
        statement.setString(2, cliente.getNombre());
        statement.setString(3, cliente.getNif());
        statement.setString(4, cliente.getDomicilio());
        statement.setString(5, cliente.tipoCliente());
        statement.setDouble(7, cliente.calcAnual());
        statement.setDouble(6, cliente.descuentoEnv());


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

    public void crearArticuloDao(Articulo articulo) throws SQLException {
        Connection connection = conexion.conectar();
        String query = "INSERT INTO articulo (codigoArticulo,descripcion,pvp,gastosEnvio,preparacionMin) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, articulo.getCodigoArticulo());
        statement.setString(2, articulo.getDescripcion());
        statement.setFloat(3, articulo.getPvp());
        statement.setDouble(4, articulo.getGastosEnvio());
        statement.setInt(5, articulo.getPreparacionMin());

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

    public void crearPedidoDao(Pedido pedido) throws SQLException {
        Connection connection = conexion.conectar();
        String query = "INSERT INTO pedido (numeroPedido,cantidad,fecha,Articulo,Cliente) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, pedido.getNumeroPedido());
        statement.setInt(2, pedido.getCantidad());
        String fechaHoraString = pedido.getFechaHora(); // obtiene la fecha y hora en formato String
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")); // crea un objeto LocalDateTime a partir del String
        Timestamp fechaHoraTimestamp = Timestamp.valueOf(fechaHora); // convierte el objeto LocalDateTime a un objeto Timestamp que se puede almacenar en MySQL
        statement.setTimestamp(3, fechaHoraTimestamp);
        statement.setString(4, pedido.getArticulo().getCodigoArticulo());
        statement.setString(5, pedido.getCliente().getMail());

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
                if (tipoCliente.equals("Standar")) {
                    cliente = new ClienteStandar(mail, nombre, nif, domicilio);
                } else if (tipoCliente.equals("Premium")) {
                    cliente = new ClientePremium(mail, nombre, nif, domicilio);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return cliente;
    }


    public Articulo recogerArticuloDAO(String codigoArticulo) throws SQLException {
        Connection connection = conexion.conectar();
        Articulo articulo = null;
        String query = "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo WHERE codigoArticulo=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, codigoArticulo);

        try {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String codigo = result.getString("codigoArticulo");
                String descripcion = result.getString("descripcion");
                float precio = result.getFloat("pvp");
                double gastosEnvio = result.getDouble("gastosEnvio");
                int preparacion = result.getInt("preparacionMin");
                // Construir un objeto Articulo
                articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);
            }
        } catch (SQLException e) {
            throw e;
        }
        return articulo;
    }

    public void mostrarClientesDAO() throws SQLException {
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
                    System.out.println(cliente.toString());
                }

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
    }

    public void mostrarStandarDAO() throws SQLException {
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
                    System.out.println(cliente.toString());
                }

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
    }

    public void mostrarPremiumDAO() throws SQLException {
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
                    System.out.println(cliente.toString());
                }

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
    }

    public void mostrarArticuloDAO() throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT codigoArticulo, descripcion, pvp, gastosEnvio, preparacionMin FROM articulo";
        PreparedStatement statement = connection.prepareStatement(query);

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String codigo = result.getString("codigoArticulo");
                String descripcion = result.getString("descripcion");
                float precio = result.getFloat("pvp");
                double gastosEnvio = result.getDouble("gastosEnvio");
                int preparacion = result.getInt("preparacionMin");

                // Construir un objeto Articulo
                Articulo articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);

                // Mostrar la información del cliente en la pantalla
                if (articulo != null) {
                    System.out.println(articulo.toString());
                }

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrar();
        }
    }

    public void eliminarPedidoDAO(int numPedido) throws SQLException {
        Connection connection = conexion.conectar();
        Pedido pedido = null;
        String query = "SELECT * FROM pedido WHERE numeroPedido=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, numPedido);

        try {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int numeroPedido = result.getInt("numeroPedido");
                int cantidad = result.getInt("cantidad");
                Timestamp fecha = result.getTimestamp("fecha");
                String articuloNombre = result.getString("Articulo");
                String clienteNombre = result.getString("Cliente");
                Cliente cliente = recogerClienteDAO(clienteNombre);
                Articulo articulo = recogerArticuloDAO(articuloNombre);
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);

                if (pedido.pedidoEnviado() == true) {
                    System.out.println("Su pedido ya ha sido enviado, no se puede eliminar.");
                } else if (pedido.pedidoEnviado() == false) {
                    System.out.println("El pedido" + pedido.toString() + "se ha eliminado correctamente");
                    String query2 = "Delete from pedido WHERE numeroPedido=?";
                    PreparedStatement statement1 = connection.prepareStatement(query2);
                    statement1.setInt(1, numPedido);
                    statement1.executeUpdate();
                }

            }
        } catch (SQLException e) {
            throw e;
        }
    }

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
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);
                if (pedido.pedidoEnviado() == false) {
                    System.out.println(pedido.toString());
                }
                // Mostrar la información del cliente en la pantalla
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

    public void mostrarPedidoEnviadoDAO() throws SQLException {
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
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);
                if (pedido.pedidoEnviado() == true) {
                    System.out.println(pedido.toString());
                    contador = contador + 1;
                }
                // Mostrar la información del cliente en la pantalla
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

    public void mostrarPedidoEnviadoFiltradoDAO(String correo) throws SQLException {
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
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);


                if (pedido.pedidoEnviado() == true && cliente.getMail().equals(correo)) {
                    System.out.println(pedido.toString());
                    contador++;
                }
                // Mostrar la información del cliente en la pantalla
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

    public void mostrarPedidoPendienteFiltradoDAO(String correo) throws SQLException {
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
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);


                if (pedido.pedidoEnviado() == false && cliente.getMail().equals(correo)) {
                    System.out.println(pedido.toString());
                    contador++;
                }
                // Mostrar la información del cliente en la pantalla
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
}

