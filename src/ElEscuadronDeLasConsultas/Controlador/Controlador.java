package ElEscuadronDeLasConsultas.Controlador;
import ElEscuadronDeLasConsultas.DAO.ArticuloDAO;
import ElEscuadronDeLasConsultas.DAO.ClienteDAO;
import ElEscuadronDeLasConsultas.DAO.PedidoDAO;
import ElEscuadronDeLasConsultas.Modelo.*;
import ElEscuadronDeLasConsultas.Modelo.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.sql.*;

public class Controlador {

    private Datos datos;
    Scanner teclado = new Scanner(System.in);
    public Controlador() {
        datos = new Datos();
    }

    //Control de articulos
    /*public void addArticulo() {
        System.out.println("Ingrese los datos del nuevo artículo:");
        System.out.println("Código: ");
        String codigo = teclado.nextLine();

        boolean existeArticulo;
        try {
            ControladorDAO controladorDAO = new ControladorDAO();
            existeArticulo = controladorDAO.existeArticuloDAO(codigo);
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del artículo.");
            return;
        }

        if (existeArticulo) {
            System.out.println("Ya existe un artículo con ese código.");
            return;
        }

        System.out.println("Descripción: ");
        String descripcion = teclado.nextLine();
        System.out.println("Precio: ");
        float precio = Float.parseFloat(teclado.nextLine());
        System.out.println("Gastos de envío: ");
        double gastosEnvio = Double.parseDouble(teclado.nextLine());
        System.out.println("Tiempo de preparación (minutos): ");
        int preparacion = Integer.parseInt(teclado.nextLine());

        Articulo articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);
        datos.getListaArticulo().add(articulo);
        try {
            ControladorDAO dao = new ControladorDAO();
            dao.crearArticuloDao(articulo);
        } catch (SQLException e) {
            // Manejar el error de alguna manera apropiada
            e.printStackTrace();
        }
        System.out.println("Artículo creado exitosamente.");
        System.out.println("\nEl Articulo creado contiene los siguientes datos:");
        System.out.println(articulo);
    }*/

    public void addArticulo(){

        ArticuloDAO AD = new ArticuloDAO();
        System.out.println("Código del Articulo");
        String codigo = teclado.nextLine();
        System.out.println("Descripción: ");
        String descripcion = teclado.nextLine();
        System.out.println("Precio: ");
        float precio = Float.parseFloat(teclado.nextLine());
        System.out.println("Gastos de envío: ");
        double gastosEnvio = Double.parseDouble(teclado.nextLine());
        System.out.println("Tiempo de preparación (minutos): ");
        int preparacion = Integer.parseInt(teclado.nextLine());

        Articulo articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);

        try {
            AD.crearArticuloDao(articulo);
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al crear el artículo: " + e.getMessage());
        }

    }

    /*public void mostrarArticulo() {
        ListaArticulo<Articulo> listaArticulos = datos.getListaArticulo();
        if (listaArticulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            System.out.println("Lista de artículos:");
            for (Articulo articulo : listaArticulos.getArrayList()) {
                System.out.println("El articulo es el siguiente:\n " + articulo.toString());
            }
        }
    }*/

    public void mostrarArticulo() {
        ArticuloDAO AD = new ArticuloDAO();
        String codigo;

        System.out.println("====================Listado de Artículos Disponibles======================");
        // Mostramos todos los artículos disponibles
        try {
            System.out.println(AD.mostrarArticuloDao());
        } catch (SQLException e) {
            System.out.println("Error al mostrar los artículos disponibles: " + e.getMessage());
        }
        System.out.println("==========================================================================\n");

        System.out.print("Introduce el código del artículo que deseas mostrar:\n");
        codigo = teclado.nextLine();

        // Mostramos el artículo solicitado
        try {
            String articuloMostrado = AD.mostrarArticuloDao();
            if (articuloMostrado == null) {
                System.out.println("No se encontró ningún artículo con el código " + codigo);
            } else {
                System.out.println(articuloMostrado);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar el artículo: " + e.getMessage());
        }
    }




    //Control de clientes:
    /*public void addCliente() {

        System.out.println("Ingrese los datos del nuevo cliente: \n Mail:");
        String mail = teclado.nextLine();
    //Función modificada para que recorra la base de datos comprobando si existe o no.

        ControladorDAO dao = new ControladorDAO();
        try {
            if (dao.existeClienteDAO(mail)) {
                System.out.println("Este Mail ya existe.");
                return;
            }

    //se siguen pidiendo los parametros necesarios

            System.out.println("Nombre: ");
            String nombre = teclado.nextLine();
            System.out.println("NIF: ");
            String nif = teclado.nextLine();
            System.out.println("Domicilio: ");
            String domicilio = teclado.nextLine();
            System.out.println("¿Es un cliente Premium? (S/N)");
            String respuesta = teclado.nextLine().toUpperCase();
            Cliente cliente;

    // Si la respuesta es 's' se crea un cliente premium y sino se creará uno estandar.

            if (respuesta.equals("S")) {
                cliente = new ClientePremium(mail, nombre, nif, domicilio);
            } else {
                cliente = new ClienteStandar(mail, nombre, nif, domicilio);
            }

    //Funcion donde incluye el cliente en la base de datos.

            try {

    //Se LLama al la funcion que permite crear el cliente en el DAO

                dao.crearClienteDao(cliente);
            } catch (SQLException e) {

    //Gestion de error de crear el cliente
                e.printStackTrace();
            }

        } catch (SQLException e) {
    //Gestión de error de el anterior try (verificar mail)
            e.printStackTrace();
        }

    }*/
    public void addCliente() throws SQLException {
        // Creamos un objeto ClienteDAO
        ClienteDAO CD = new ClienteDAO();
        // Pedimos al usuario que introduzca el mail del cliente
        System.out.print("Introduce el mail: ");
        String mail = teclado.nextLine();
        // Comprobamos si el cliente ya está registrado en la base de datos
        boolean existeClienteDAO = CD.existeClienteDAO(mail);

        if (existeClienteDAO) {
            // Si el cliente ya está registrado, lo notificamos al usuario
            System.out.println("\n\t¡Este cliente ya está registrado!");
        } else {
            // Si el cliente no está registrado, pedimos los datos necesarios para crearlo
            System.out.println("Nombre: ");
            String nombre = teclado.nextLine();
            System.out.println("NIF: ");
            String nif = teclado.nextLine();
            System.out.println("Domicilio: ");
            String domicilio = teclado.nextLine();

            // Preguntamos si el cliente es Premium
            System.out.println("¿Es un cliente Premium? (S/N)");
            String respuesta = teclado.nextLine().toUpperCase();
            Cliente cliente;
            if (respuesta.equals("S")) {
                // Si el cliente es Premium, creamos un objeto ClientePremium
                cliente = new ClientePremium(mail, nombre, nif, domicilio);
            } else {
                // Si el cliente no es Premium, creamos un objeto ClienteStandard
                cliente = new ClienteStandar(mail, nombre, nif, domicilio);
            }

            try {
                // Intentamos crear el cliente en la base de datos
                CD.crearClienteDao(cliente);
            } catch (SQLException e) {
                // Si ocurre un error al crear el cliente, notificamos al usuario e imprimimos la traza del error
                System.out.println("Ha ocurrido un error al crear el cliente: ");
                e.printStackTrace();
            }
        }
    }

    /*public void mostrarCliente() {
        ListaCliente<Cliente> listaCliente = datos.getListaCliente();
        if (listaCliente.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
        } else {
            System.out.println("Lista de Clientes: \n");
            for (Cliente cliente : listaCliente.getArrayList()) {
                if (cliente.tipoCliente() == "Standar") {

                    System.out.println(cliente);
                } else {
                    System.out.println(cliente);
                }
            }
        }
    }*/

    public void mostrarCliente(){
            try {
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.mostrarClientesDAO();
            } catch (SQLException e) {
                System.out.println("Error al mostrar los clientes: " + e.getMessage());
            }
        }


    public void mostrarClienteStandar() throws SQLException {
        ClienteDAO CD = new ClienteDAO();
        System.out.println(CD.mostrarClienteStandarDAO());
    }

/*
    public void mostrarClienteStandar() {
        ListaCliente<Cliente> listaCliente = datos.getListaCliente();
        if (listaCliente.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
        } else {
            System.out.println("Lista de Clientes: \n");
            for (Cliente cliente : listaCliente.getArrayList()) {
                if(cliente.tipoCliente() == "Standar"){

                    System.out.println(cliente);}
            }
        }
    }*/

    public void mostrarClientePremium()throws SQLException{
        ClienteDAO CD = new ClienteDAO();
        System.out.println(CD.mostrarClientePremiumDAO());
    }
    /*public void mostrarClientePremium() {
        ListaCliente<Cliente> listaCliente = datos.getListaCliente();
        if (listaCliente.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
        } else {
            System.out.println("Lista de Clientes: \n");
            for (Cliente cliente : listaCliente.getArrayList()) {
                if(cliente.tipoCliente() == "Premium"){

                    System.out.println(cliente);}
            }
        }
    }*/

    // controlador Pedido
   /* public void addPedido() {
        System.out.println("Ingrese los datos del pedido");
        System.out.println("Codigo pedido:");
        int numeroPedido = teclado.nextInt();
        boolean existePedido = false;
        try {
            ControladorDAO controladorDAO = new ControladorDAO();
            existePedido = controladorDAO.existePedidoDAO(numeroPedido);
        } catch (SQLException e) {
            System.out.println("Error al buscar el pedido en la base de datos: " + e.getMessage());
            return;
        } finally {
            if (existePedido) {
                System.out.println("Este Pedido ya existe");
                return;
            }
        }
        System.out.println("Cantidad:");
        int cantidad = teclado.nextInt();
        System.out.println("Codigo del articulo");
        teclado.nextLine();
        String codigo = teclado.nextLine();
        boolean existeArticulo;
        try {
            ControladorDAO controladorDAO = new ControladorDAO();
            existeArticulo = controladorDAO.existeArticuloDAO(codigo);
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del artículo.");
            return;
        }

        if (existeArticulo) {
            System.out.println("Ya existe un artículo con ese código.");
            return;
        }
        ListaArticulo<Articulo> listaArticulos = datos.getListaArticulo();
        Articulo articulo = listaArticulos.obtenerCodigoArticulo(codigo);

        if (articulo == null) {
            System.out.println("El articulo no existe.");
        } else {
            System.out.println("Mail del cliente:");
            String mail = teclado.nextLine();

            ListaCliente<Cliente> listaCliente = datos.getListaCliente();
            Cliente cliente = listaCliente.obtenerClientePorMail(mail);

            if (cliente == null) {

                System.out.println("Cliente no encontrado, añada sus datos\n");
                System.out.println("Nombre: ");
                String nombre = teclado.nextLine();
                System.out.println("NIF: ");
                String nif = teclado.nextLine();
                System.out.println("Domicilio: ");
                String domicilio = teclado.nextLine();

                System.out.println("¿Es un cliente Premium? (S/N)");
                String respuesta = teclado.nextLine().toUpperCase();
                Cliente clientes;
                if (respuesta.equals("S")) {
                    clientes = new ClientePremium(mail, nombre, nif, domicilio);
                } else {
                    clientes = new ClienteStandar(mail, nombre, nif, domicilio);
                }
                ControladorDAO dao = new ControladorDAO();
                try {
                    dao.crearClienteDao(cliente);
                } catch (SQLException e) {
                    // Manejar el error de alguna manera apropiada
                    e.printStackTrace();
                }
                datos.getListaCliente().add(clientes);
                listaCliente = datos.getListaCliente();
                cliente = clientes;
                datos.setListaCliente(listaCliente);
            }
            Pedido pedido = new Pedido(numeroPedido, cantidad, articulo, cliente);
            ControladorDAO dao = new ControladorDAO();
            try {
                dao.crearPedidoDao(pedido);
            } catch (SQLException e) {
                // Manejar el error de alguna manera apropiada
                e.printStackTrace();
            }
            datos.getListaPedido().add(pedido);
            System.out.println(pedido);
        }
    }*/

    public void addPedido() {
        PedidoDAO pedidoDAO = new PedidoDAO();

        // Solicitar los datos del pedido al usuario
        System.out.println("Introduce los datos del pedido:");
        System.out.println("Número de pedido:");
        int numeroPedido = Integer.parseInt(teclado.nextLine());
        System.out.println("Cantidad:");
        int cantidad = Integer.parseInt(teclado.nextLine());
        System.out.println("Fecha (dd/MM/yyyy HH:mm:ss):");
        String fechaHoraString = teclado.nextLine();
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraString, DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
        System.out.println("Código del artículo:");
        String codigoArticulo = teclado.nextLine();
        System.out.println("Correo electrónico del cliente:");
        String mailCliente = teclado.nextLine();

        // Crear el objeto Articulo y el objeto Cliente correspondientes
        Articulo articulo = new Articulo(codigoArticulo, null, 0, 0, 0);
        Cliente cliente = null;

        try {
            // Obtener el tipo de cliente a partir del correo electrónico
            cliente = pedidoDAO.recogerClienteDAO(mailCliente);

            // Verificar que el cliente exista y sea de tipo Premium
            if (cliente == null) {
                System.out.println("El cliente no existe.");
                return;
            } else if (!(cliente instanceof ClientePremium)) {
                System.out.println("El cliente no es de tipo Premium.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al buscar el cliente: " + e.getMessage());
            return;
        }

        // Crear el objeto Pedido correspondiente
        Pedido pedido = new Pedido(numeroPedido, cantidad, articulo, (ClientePremium) cliente, fechaHora);

        // Insertar el pedido en la base de datos
        try {
            pedidoDAO.crearPedidoDAO(pedido);
            System.out.println("Pedido creado correctamente.");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al crear el pedido: " + e.getMessage());
        }
    }




    public void eliminarPedido () {
            System.out.println("Ingrese el numero de pedido que desea eliminar:");
            int codigo = teclado.nextInt();

            ListaPedido<Pedido> listaPedido = datos.getListaPedido();
            Pedido pedido = listaPedido.obtenerPorNumPedido(codigo);
            if (pedido == null) {
                System.out.println("Pedido no encontrado.");
            } else {
                if (pedido.pedidoEnviado() == false) {
                    listaPedido.borrar(pedido);
                    System.out.println("Pedido eliminado con éxito.");
                }else{
                    System.out.println("No se puede eliminar el articulo, ya ha sido enviado.");
                }

            }
        }

        public void mostrarPedidosPendientes () {
            ListaPedido<Pedido> listapedido = datos.getListaPedido();
            if (listapedido.getSize() == 0) {
                System.out.println("No hay pedidos que mostrar.");
            } else {
                System.out.println("Envios Pendientes:");
                for (Pedido pedido : listapedido.getArrayList()) {
                    if (pedido.pedidoEnviado() == false) {
                        System.out.println(pedido);
                    }
                }
            }
        }
        public void mostrarPedidosEnviados () {
                ListaPedido<Pedido> listapedido = datos.getListaPedido();
                if (listapedido.getSize() == 0) {
                    System.out.println("No hay pedidos que mostrar.");
                } else {
                    System.out.println("Envios Pendientes:");
                    for (Pedido pedido : listapedido.getArrayList()) {
                        if (pedido.pedidoEnviado() == true) {
                            System.out.println(pedido);
                        }
                    }
                }
            }
}
