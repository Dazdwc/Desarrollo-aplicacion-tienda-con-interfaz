package ElEscuadronDeLasConsultas.Controlador;
import ElEscuadronDeLasConsultas.Modelo.*;
import java.util.Scanner;
import java.sql.*;

public class Controlador {

    private Datos datos;
    Scanner teclado = new Scanner(System.in);
    public Controlador() {
        datos = new Datos();
    }

    //Control de articulos
    public void addArticulo() {
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
        try {
            ControladorDAO dao = new ControladorDAO();
            dao.crearArticuloDao(articulo);
        } catch (SQLException e) {
            // Manejar el error de alguna manera apropiada
            e.printStackTrace();
        }
        System.out.println("Artículo creado exitosamente.");
    }


    public void mostrarArticulo() {
        ControladorDAO cont = new ControladorDAO();
        try {
            cont.mostrarArticuloDAO();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }
    }


    //Control de clientes:
    public void addCliente() {

        System.out.println("Ingrese los datos del nuevo cliente: \nMail:");
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

    }

    public void mostarCliente() {
        ControladorDAO cont = new ControladorDAO();
        try {
            cont.mostrarClientesDAO();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }
    }


    public void mostarClienteStandar() {

        ControladorDAO cont = new ControladorDAO();
        try {
            cont.mostrarStandarDAO();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }
    }
    public void mostarClientePremium() {
        ControladorDAO cont = new ControladorDAO();
        try {
            cont.mostrarPremiumDAO();
        } catch (SQLException e) {
            System.out.println("Error al mostrar los clientes: " + e.getMessage());
        }

    }

    // controlador Pedido
    public void addPedido() {
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

            ControladorDAO controladorDAO = new ControladorDAO();
            Articulo articulo = null;
            try {
                articulo = controladorDAO.recogerArticuloDAO(codigo);
            } catch (SQLException e) {
                System.out.println("Error al recoger el artículo: " + e.getMessage());
                return;
            }
            if (articulo == null) {
                System.out.println("El articulo que quiere seleccionar no existe.");
                mostrarArticulo();
                return;
        } else {
                System.out.println("Mail del cliente:");
                String mail = teclado.nextLine();

                ControladorDAO controladorDAO1 = new ControladorDAO();
                Cliente cliente;
                try {
                    cliente = controladorDAO1.recogerClienteDAO(mail);
                } catch (SQLException e) {
                    System.out.println("Error al recoger el cliente: " + e.getMessage());
                    return;
                }
                if (cliente == null) {
                    System.out.println("El cliente no existe, creelo:");
                    addCliente();
                    try {
                        cliente = controladorDAO1.recogerClienteDAO(mail);
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
                ControladorDAO dao = new ControladorDAO();
                try {
                    dao.crearPedidoDao(pedido);
                } catch (SQLException e) {
                    // Manejar el error de alguna manera apropiada
                    e.printStackTrace();
                }
            }
    }

        public void eliminarPedido () {
            System.out.println("Ingrese el numero de pedido que desea eliminar:");
            int codigo = teclado.nextInt();

            ControladorDAO controladorDAO = new ControladorDAO();
                try {
                    controladorDAO.eliminarPedidoDAO(codigo);
                } catch (SQLException e) {
                    System.out.println("Error al mostrar los pedidos: " + e.getMessage());
                }
            }

    public void mostrarPedidoPendiente() {
        System.out.println("¿Quiere filtrar por cliente? (S/N) \nSi no lo filtra se mostrarán todos.\n");
        String respuesta = teclado.nextLine().toUpperCase();
        ControladorDAO controladorDAO = new ControladorDAO();

        if(respuesta.equalsIgnoreCase("S")) {
            System.out.println("Ingrese el correo electrónico del cliente:");
            String correoElectronico = teclado.nextLine();
            try {
                controladorDAO.mostrarPedidoPendienteFiltradoDAO(correoElectronico);
            } catch (SQLException e) {
                System.out.println("Error al mostrar los pedidos: " + e.getMessage());
            }
        }else{
            try {
                controladorDAO.mostrarPedidoPendienteDAO();
            } catch (SQLException e) {
                System.out.println("Error al mostrar los pedidos: " + e.getMessage());
            }
        }
    }

        public void mostrarPedidoEnviado() {
            System.out.println("¿Quiere filtrar por cliente? (S/N) \nSi no lo filtra se mostrarán todos.\n");
            String respuesta = teclado.nextLine().toUpperCase();
            ControladorDAO controladorDAO = new ControladorDAO();

            if(respuesta.equalsIgnoreCase("S")) {
                System.out.println("Ingrese el correo electrónico del cliente:");
                String correoElectronico = teclado.nextLine();
                try {
                    controladorDAO.mostrarPedidoEnviadoFiltradoDAO(correoElectronico);
                } catch (SQLException e) {
                    System.out.println("Error al mostrar los pedidos: " + e.getMessage());
                }
            }else{
                try {
                    controladorDAO.mostrarPedidoEnviadoDAO();
                } catch (SQLException e) {
                    System.out.println("Error al mostrar los pedidos: " + e.getMessage());
                }
            }
        }
}
