package ElEscuadronDeLasConsultas.Modelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PedidoDAOimp implements PedidoDAO {
    private final Conexion conexion = new Conexion();


    public boolean existePedido(int numeroPedido) throws SQLException {
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

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

    public void crearPedido(Pedido pedido) throws SQLException {
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

    public void eliminarPedido(int numPedido) throws SQLException {
        Connection connection = conexion.conectar();
        Pedido pedido;
        String query = "SELECT * FROM pedido WHERE numeroPedido=?";
        ClienteDAO cdao = new ClienteDAOimp();
        ArticuloDAO adao = new ArticuloDAOimp();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, numPedido);

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            int numeroPedido = result.getInt("numeroPedido");
            int cantidad = result.getInt("cantidad");
            Timestamp fecha = result.getTimestamp("fecha");
            String art = result.getString("Articulo");
            String cli = result.getString("Cliente");


            Cliente cliente = cdao.recogerCliente(cli);
            Articulo articulo = adao.recogerArticulo(art);

            // Convertir Timestamp a LocalDateTime
            LocalDateTime fechaConver = fecha.toLocalDateTime();

            // Construir un objeto Pedido
            pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);

            if (pedido.pedidoEnviado()) {
                System.out.println("Su pedido ya ha sido enviado, no se puede eliminar.");
            } else if (!pedido.pedidoEnviado()) {
                System.out.println("El pedido numero:" + pedido.getNumeroPedido() + "se ha eliminado correctamente");
                String query2 = "Delete from pedido WHERE numeroPedido=?";
                PreparedStatement statement1 = connection.prepareStatement(query2);
                statement1.setInt(1, numPedido);
                statement1.executeUpdate();
            }

        }
    }
        public void mostrarPedidoPendiente() throws SQLException {
            Connection connection = conexion.conectar();
            ClienteDAO cdao = new ClienteDAOimp();
            ArticuloDAO adao = new ArticuloDAOimp();
            String query = "SELECT numeroPedido,cantidad,fecha,Articulo,Cliente FROM pedido";
            PreparedStatement statement = connection.prepareStatement(query);
            int contador = 0;
            ResultSet result;

            try {
                result = statement.executeQuery();
                while (result.next()) {
                    int numeroPedido = result.getInt("numeroPedido");
                    int cantidad = result.getInt("cantidad");
                    Timestamp fecha = result.getTimestamp("fecha");
                    String art = result.getString("Articulo");
                    String cli = result.getString("Cliente");

                    Cliente cliente = cdao.recogerCliente(cli);
                    Articulo articulo = adao.recogerArticulo(art);
                    // Convertir Timestamp a LocalDateTime
                    LocalDateTime fechaConver = fecha.toLocalDateTime();

                    // Construir un objeto Pedido
                    Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);
                    if (!pedido.pedidoEnviado()) {
                        System.out.println(pedido);
                        contador++;
                    }
                    // Mostrar la información del cliente en la pantalla
                }
                if (contador == 0) {
                    System.out.println("no existen pedidos que mostrar");
                }
            } finally {
                conexion.cerrar();
            }
        }

    public void mostrarPedidoEnviado() throws SQLException {
        Connection connection = conexion.conectar();
        ClienteDAO cdao = new ClienteDAOimp();
        ArticuloDAO adao = new ArticuloDAOimp();
        String query = "SELECT numeroPedido,cantidad,fecha,Articulo,Cliente FROM pedido";
        PreparedStatement statement = connection.prepareStatement(query);
        int contador = 0;
        ResultSet result;

        try {
            result = statement.executeQuery();
            while (result.next()) {
                int numeroPedido = result.getInt("numeroPedido");
                int cantidad = result.getInt("cantidad");
                Timestamp fecha = result.getTimestamp("fecha");
                String art = result.getString("Articulo");
                String cli = result.getString("Cliente");

                Cliente cliente = cdao.recogerCliente(cli);
                Articulo articulo = adao.recogerArticulo(art);
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);
                if (pedido.pedidoEnviado()) {
                    System.out.println(pedido);
                    contador++;
                }
                // Mostrar la información del cliente en la pantalla
            }
            if (contador == 0) {
                System.out.println("no existen pedidos que mostrar");
            }
        } finally {
            conexion.cerrar();
        }
    }
    public void mostrarPedidoEnviadoFiltrado(String correo) throws SQLException {
        Connection connection = conexion.conectar();
        String query = "SELECT numeroPedido,cantidad,fecha,Articulo,Cliente FROM pedido";
        PreparedStatement statement = connection.prepareStatement(query);
        ClienteDAO cdao = new ClienteDAOimp();
        ArticuloDAO adao = new ArticuloDAOimp();
        int contador = 0;
        ResultSet result;

        try {
            result = statement.executeQuery();
            while (result.next()) {
                int numeroPedido = result.getInt("numeroPedido");
                int cantidad = result.getInt("cantidad");
                Timestamp fecha = result.getTimestamp("fecha");
                String art = result.getString("Articulo");
                String cli = result.getString("Cliente");

                Cliente cliente = cdao.recogerCliente(cli);
                Articulo articulo = adao.recogerArticulo(art);
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);


                if (pedido.pedidoEnviado() && cliente.getMail().equals(correo)) {
                    System.out.println(pedido);
                    contador++;
                }
                // Mostrar la información del cliente en la pantalla
            }
            if (contador == 0) {
                System.out.println("no existen pedidos que mostrar");
            }
        } finally {
            conexion.cerrar();
        }
    }
    public void mostrarPedidoPendienteFiltrado(String correo) throws SQLException {
        ClienteDAO cdao = new ClienteDAOimp();
        ArticuloDAO adao = new ArticuloDAOimp();
        Connection connection = conexion.conectar();
        String query = "SELECT numeroPedido,cantidad,fecha,Articulo,Cliente FROM pedido";
        PreparedStatement statement = connection.prepareStatement(query);
        int contador = 0;
        ResultSet result;

        try {
            result = statement.executeQuery();
            while (result.next()) {
                int numeroPedido = result.getInt("numeroPedido");
                int cantidad = result.getInt("cantidad");
                Timestamp fecha = result.getTimestamp("fecha");
                String art = result.getString("Articulo");
                String cli = result.getString("Cliente");

                Cliente cliente = cdao.recogerCliente(cli);
                Articulo articulo = adao.recogerArticulo(art);
                // Convertir Timestamp a LocalDateTime
                LocalDateTime fechaConver = fecha.toLocalDateTime();

                // Construir un objeto Pedido
                Pedido pedido = new Pedido(numeroPedido, cantidad, fechaConver, articulo, cliente);


                if (!pedido.pedidoEnviado() && cliente.getMail().equals(correo)) {
                    System.out.println(pedido);
                    contador++;
                }
                // Mostrar la información del cliente en la pantalla
            }
            if (contador == 0) {
                System.out.println("no existen pedidos que mostrar");
            }
        } finally {
            conexion.cerrar();
        }
    }

}


