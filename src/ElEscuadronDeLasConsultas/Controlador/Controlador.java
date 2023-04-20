package ElEscuadronDeLasConsultas.Controlador;
import ElEscuadronDeLasConsultas.Modelo.*;
import java.util.Scanner;
import java.sql.*;

public class Controlador {

    Scanner teclado = new Scanner(System.in);

    //Control de articulos
    public void addArticulo(){
        System.out.println("Ingrese los datos del nuevo artículo:");
        System.out.println("Código: ");
        String codigo = teclado.nextLine();
        ArticuloDAO articuloDAO = new ArticuloDAOimp();

        boolean existeArticulo;
        try {
            existeArticulo = articuloDAO.existeArticulo(codigo);
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
        try {
            FactoryDAO factory = new FactoryMySQLDAO();
            ArticuloDAO art = factory.crearArticuloDAO();
            art.crearArticulo(articulo);
        } catch (SQLException e) {
            // Manejar el error de alguna manera apropiada
            e.printStackTrace();
        }
        System.out.println("Artículo creado exitosamente.");
    }


    public void mostrarArticulo() {
        ArticuloDAO art = new ArticuloDAOimp();
        try {
            art.mostrarArticulo();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }
    }


    //Control de clientes:
    public void addCliente() {

        System.out.println("Ingrese los datos del nuevo cliente: \nMail:");
        String mail = teclado.nextLine();
    //Función modificada para que recorra la base de datos comprobando si existe o no.
       ClienteDAO dao = new ClienteDAOimp();
        try {
            if (dao.existeCliente(mail)) {
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
                FactoryDAO factory = new FactoryMySQLDAO();
                ClienteDAO cli = factory.crearClienteDAO();
                cli.crearCliente(cliente);

            } catch (SQLException e) {

    //Gestion de error de crear el cliente
                e.printStackTrace();
            }

        } catch (SQLException e) {
    //Gestión de error de el anterior try (verificar mail)
            e.printStackTrace();
        }

    }

    public void mostarCliente() {
        ClienteDAO clienteDAO = new ClienteDAOimp();
        try {
            clienteDAO.mostrarClientes();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }
    }


    public void mostarClienteStandar() {

        ClienteDAO clienteDAO = new ClienteDAOimp();
        try {
            clienteDAO.mostrarStandar();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }
    }
    public void mostarClientePremium() {
        ClienteDAO clienteDAO = new ClienteDAOimp();
        try {
            clienteDAO.mostrarPremium();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }

    }

    // controlador Pedido
    public void addPedido() {
        System.out.println("Ingrese los datos del pedido");
        System.out.println("Codigo pedido:");
        int numeroPedido = teclado.nextInt();
        boolean existePedido;
        try {
            PedidoDAO pedidoDAO = new PedidoDAOimp();
            existePedido = pedidoDAO.existePedido(numeroPedido);
        } catch (SQLException e) {
            System.out.println("Error al buscar el pedido en la base de datos: " + e.getMessage());
            return;
        }if (existePedido) {
                System.out.println("Este Pedido ya existe");
                return;
            }
        System.out.println("Cantidad:");
        int cantidad = teclado.nextInt();
        System.out.println("Codigo del articulo");
        teclado.nextLine();
        String codigo = teclado.nextLine();

            ArticuloDAO articuloDAO = new ArticuloDAOimp();

            Articulo articulo;
            try {
                articulo = articuloDAO.recogerArticulo(codigo);
            } catch (SQLException e) {
                System.out.println("Error al recoger el artículo: " + e.getMessage());
                return;
            }
            if (articulo == null) {
                System.out.println("El articulo que quiere seleccionar no existe.");
        } else {
                System.out.println("Mail del cliente:");
                String mail = teclado.nextLine();

                ClienteDAO clienteDAO = new ClienteDAOimp();
                Cliente cliente;
                try {
                    cliente = clienteDAO.recogerCliente(mail);
                } catch (SQLException e) {
                    System.out.println("Error al recoger el cliente: " + e.getMessage());
                    return;
                }
                if (cliente == null) {
                    System.out.println("El cliente no existe, creelo:");
                        addCliente();
                    try {
                        cliente = clienteDAO.recogerCliente(mail);
                    } catch (SQLException e) {
                        System.out.println("Error al recoger el cliente: " + e.getMessage());
                        return;
                    }
                }
                if (cliente == null) {
                    System.out.println("No se pudo crear el cliente.");
                    return;
                }
                Pedido pedido = new Pedido(numeroPedido, cantidad, articulo, cliente);
                FactoryDAO factory = new FactoryMySQLDAO();
                PedidoDAO ped = factory.crearPedidoDAO();

                try {
                    ped.crearPedido(pedido);
                } catch (SQLException e) {
                    // Manejar el error de alguna manera apropiada
                    e.printStackTrace();
                }
            }
    }

        public void eliminarPedido () {
            System.out.println("Ingrese el numero de pedido que desea eliminar:");
            int codigo = teclado.nextInt();

            PedidoDAO pedidoDAO = new PedidoDAOimp();
                try {
                    pedidoDAO.eliminarPedido(codigo);
                } catch (SQLException e) {
                    System.out.println("Error al mostrar los pedidos: " + e.getMessage());
                }
            }

    public void mostrarPedidoPendiente() {
        System.out.println("¿Quiere filtrar por cliente? (S/N) \nSi no lo filtra se mostrarán todos.\n");
        String respuesta = teclado.nextLine().toUpperCase();
        PedidoDAO pedidoDAO = new PedidoDAOimp();

        if(respuesta.equalsIgnoreCase("S")) {
            System.out.println("Ingrese el correo electrónico del cliente:");
            String correoElectronico = teclado.nextLine();
            try {
                pedidoDAO.mostrarPedidoPendienteFiltrado(correoElectronico);
            } catch (SQLException e) {
                System.out.println("Error al mostrar los pedidos: " + e.getMessage());
            }
        }else{
            try {
                pedidoDAO.mostrarPedidoPendiente();
            } catch (SQLException e) {
                System.out.println("Error al mostrar los pedidos: " + e.getMessage());
            }
        }
    }

        public void mostrarPedidoEnviado() {
            System.out.println("¿Quiere filtrar por cliente? (S/N) \nSi no lo filtra se mostrarán todos.\n");
            String respuesta = teclado.nextLine().toUpperCase();
            PedidoDAO pedidoDAO = new PedidoDAOimp();

            if(respuesta.equalsIgnoreCase("S")) {
                System.out.println("Ingrese el correo electrónico del cliente:");
                String correoElectronico = teclado.nextLine();
                try {
                    pedidoDAO.mostrarPedidoEnviadoFiltrado(correoElectronico);
                } catch (SQLException e) {
                    System.out.println("Error al mostrar los pedidos: " + e.getMessage());
                }
            }else{
                try {
                    pedidoDAO.mostrarPedidoEnviado();
                } catch (SQLException e) {
                    System.out.println("Error al mostrar los pedidos: " + e.getMessage());
                }
            }
        }
}
