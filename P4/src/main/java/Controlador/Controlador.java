package Controlador;
import Modelo.*;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Controlador {
    Scanner teclado = new Scanner(System.in);
    public void addArticulo() {
        // Solicitar datos al usuario
        System.out.println("Ingrese los datos del nuevo artículo:");
        System.out.println("Código: ");
        String codigo = teclado.nextLine();

        // Verificar si el artículo ya existe
        boolean existeArticulo;
        try {
            FactoryDAO factory = new FactoryDAOImp();
            ArticuloDAO art = factory.createArticuloDAO();
            existeArticulo = art.existeArticulo(codigo);
        } catch (HibernateException e) {
            System.out.println("Error al verificar la existencia del artículo.");
            return;
        }

        if (existeArticulo) {
            System.out.println("Ya existe un artículo con ese código.");
            return;
        }

        // Solicitar datos adicionales al usuario
        System.out.println("Descripción: ");
        String descripcion = teclado.nextLine();
        System.out.println("Precio: ");
        float precio = Float.parseFloat(teclado.nextLine());
        System.out.println("Gastos de envío: ");
        double gastosEnvio = Double.parseDouble(teclado.nextLine());
        System.out.println("Tiempo de preparación (minutos): ");
        int preparacion = Integer.parseInt(teclado.nextLine());

        // Crear el nuevo artículo y guardarlo en la base de datos
        Articulo articulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacion);
        try {
            FactoryDAO factory = new FactoryDAOImp();
            ArticuloDAO art = factory.createArticuloDAO();
            art.create(articulo);
        } catch (HibernateException e) {
            // Manejar el error
            e.printStackTrace();
        }
        System.out.println("Artículo creado exitosamente.");
    }

    public void mostrarArticulos() {
        // Crear instancia de FactoryDAO
        FactoryDAO factory = new FactoryDAOImp();

        // Crear instancia de ClienteDAO
        ArticuloDAO articuloDAO = factory.createArticuloDAO();
        // Obtener y mostrar todos los Articulos
        System.out.println("\nArticulos:");

        List<Articulo> articulo = articuloDAO.getAll();
            System.out.println(articulo);
    }

    public void addCliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("onlinestore");
        EntityManager em = emf.createEntityManager();
        ClienteDAO dao = new ClienteDAOImp(em);
        // Se solicita al usuario que ingrese los datos del nuevo cliente
        System.out.println("Ingrese los datos del nuevo cliente: \nMail:");
        String mail = teclado.nextLine();

        // Se verifica si el cliente ya existe en la base de datos

        try {
            if (dao.existeCliente(mail)) {
                System.out.println("Este Mail ya existe.");
                return;
            }

            // Se siguen pidiendo los parámetros necesarios

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

            // Se crea el cliente en la base de datos

            try {
                FactoryDAO factory = new FactoryDAOImp();
                ClienteDAO cli = factory.createClienteDAO();
                cli.create(cliente);

            } catch (HibernateException e) {

                // Gestion de error al crear el cliente
                e.printStackTrace();
            }

        } catch (HibernateException e) {
            // Gestion de error al verificar la existencia del cliente
            e.printStackTrace();
        }

    }

    /**
     * Muestra todos los clientes guardados en la base de datos.
     */
    public void mostarCliente() {
        // Crear instancia de FactoryDAO
        FactoryDAO factory = new FactoryDAOImp();

        // Crear instancia de ClienteDAO
        ClienteDAO clienteDAO = factory.createClienteDAO();

        // Obtener y mostrar todos los clientes estándar
        System.out.println("\nClientes Estándar:");
        List<Cliente> clientesStandar = clienteDAO.getAll();
        for (Cliente cliente : clientesStandar) {
            System.out.println(cliente);
        }
    }
    /**
     * Muestra todos los clientes standar guardados en la base de datos.
     */
    public void mostarClienteStandar() {
        // Crear instancia de FactoryDAO
        FactoryDAO factory = new FactoryDAOImp();

        // Crear instancia de ClienteDAO
        ClienteDAO clienteDAO = factory.createClienteDAO();

        // Obtener y mostrar todos los clientes estándar
        System.out.println("\nClientes Estándar:");
        List<ClienteStandar> clientesStandar = clienteDAO.getAllStandar();
        for (ClienteStandar cliente : clientesStandar) {
            System.out.println(cliente);
        }
    }
    /**
     * Muestra todos los clientes premium guardados en la base de datos.
     */
    public void mostarClientePremium() {
        // Crear instancia de FactoryDAO
        FactoryDAO factory = new FactoryDAOImp();

        // Crear instancia de ClienteDAO
        ClienteDAO clienteDAO = factory.createClienteDAO();

        // Obtener y mostrar todos los clientes premium
        System.out.println("Clientes Premium:");
        List<ClientePremium> clientesPremium = clienteDAO.getAllPremium();
        for (ClientePremium cliente : clientesPremium) {
            System.out.println(cliente);
        }
    }
    public void addPedido() {
        System.out.println("Ingrese los datos del pedido");
        System.out.println("Codigo pedido:");
        int numeroPedido = teclado.nextInt();

        boolean existePedido;
        try {
            FactoryDAO factory = new FactoryDAOImp();
            PedidoDAO pedidoDAO = factory.createPedidoDAO();
            existePedido = pedidoDAO.existePedido(numeroPedido);
        } catch (HibernateException e) {
            System.out.println("Error al buscar el pedido en la base de datos: " + e.getMessage());
            return;
        }
        if (existePedido) {
            System.out.println("Este Pedido ya existe");
            return;
        }
        System.out.println("Cantidad:");
        int cantidad = teclado.nextInt();
        System.out.println("Codigo del articulo");
        teclado.nextLine();
        String codigo = teclado.nextLine();

        FactoryDAO factory = new FactoryDAOImp();
        ArticuloDAO articuloDAO = factory.createArticuloDAO();

        Articulo articulo;
        try {
            articulo = articuloDAO.getArticulo(codigo);
        } catch (HibernateException e) {
            System.out.println("Error al recoger el artículo: " + e.getMessage());
            return;
        }
        if (articulo == null) {
            System.out.println("El articulo que quiere seleccionar no existe.");
        } else {
            System.out.println("Mail del cliente:");
            String mail = teclado.nextLine();

            ClienteDAO clienteDAO = factory.createClienteDAO();
            Cliente cliente;
            try {
                cliente = clienteDAO.getCliente(mail);
            } catch (HibernateException e) {
                System.out.println("Error al recoger el cliente: " + e.getMessage());
                return;
            }
            if (cliente == null) {
                System.out.println("El cliente no existe, creelo:");
                addCliente();
                try {
                    cliente = clienteDAO.getCliente(mail);
                } catch (HibernateException e) {
                    System.out.println("Error al recoger el cliente: " + e.getMessage());
                    return;
                }
            }
            if (cliente == null) {
                System.out.println("No se pudo crear el cliente.");
                return;
            }
            Pedido pedido = new Pedido(numeroPedido, cantidad, articulo, cliente);
            PedidoDAO ped = factory.createPedidoDAO();

            try {
                ped.create(pedido);
            } catch (HibernateException e) {
                // Manejar el error de alguna manera apropiada
                e.printStackTrace();
            }
        }
    }
    public void eliminarPedido() {
        System.out.println("Ingrese el numero de pedido que desea eliminar:");
        int codigo = teclado.nextInt();

        FactoryDAO factory = new FactoryDAOImp();
        PedidoDAO pedidoDAO = factory.createPedidoDAO();
        Pedido pedido = pedidoDAO.getPedido(codigo);

        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        if (pedido.pedidoEnviado()) {
            System.out.println("Su pedido ya ha sido enviado, no se puede eliminar.");
        } else {
            try {
                pedidoDAO.delete(pedido);
                System.out.println("El pedido numero: " + pedido.getNumeroPedido() + " se ha eliminado correctamente");
            } catch (HibernateException e) {
                System.out.println("Error al eliminar el pedido: " + e.getMessage());
            }
        }
    }



    public void mostrarPedidoPendiente() {
        System.out.println("¿Quiere filtrar por cliente? (S/N) \nSi no lo filtra se mostrarán todos.\n");
        String respuesta = teclado.nextLine().toUpperCase();
        String correoElectronico = "";

        // Utiliza la FactoryDAO para obtener la instancia de PedidoDAO
        FactoryDAO factory = new FactoryDAOImp();
        PedidoDAO pedidoDAO = factory.createPedidoDAO();

        if (respuesta.equals("S")) {
            System.out.println("Ingrese el correo electrónico del cliente:");
            correoElectronico = teclado.nextLine();
            pedidoDAO.mostrarPedidoPendienteFiltrado(correoElectronico);
        } else if (respuesta.equals("N")) {
            pedidoDAO.mostrarPedidoPendiente();
        } else {
            System.out.println("Opción inválida, por favor ingrese S o N.");
        }
    }

    public void mostrarPedidoEnviado() {
        System.out.println("¿Quiere filtrar por cliente? (S/N) \nSi no lo filtra se mostrarán todos.\n");
        String respuesta = teclado.nextLine().toUpperCase();
        String correoElectronico = "";

        // Utiliza la FactoryDAO para obtener la instancia de PedidoDAO
        FactoryDAO factory = new FactoryDAOImp();
        PedidoDAO pedidoDAO = factory.createPedidoDAO();

        if (respuesta.equals("S")) {
            System.out.println("Ingrese el correo electrónico del cliente:");
            correoElectronico = teclado.nextLine();
            pedidoDAO.mostrarPedidoEnviadoFiltrado(correoElectronico);
        } else if (respuesta.equals("N")) {
            pedidoDAO.mostrarPedidoEnviado();
        } else {
            System.out.println("Opción inválida, por favor ingrese S o N.");
        }
    }




}
