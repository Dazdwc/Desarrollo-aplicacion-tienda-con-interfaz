package ElEscuadronDeLasConsultas.Controlador;
import ElEscuadronDeLasConsultas.Modelo.*;
import java.util.Scanner;

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
        int codigo = Integer.parseInt(teclado.nextLine());
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
        System.out.println("Artículo creado exitosamente.");
        System.out.println("\nEl Articulo creado contiene los siguientes datos:");
        System.out.println(articulo);
    }


    public void mostrarArticulo() {
        ListaArticulo<Articulo> listaArticulos = datos.getListaArticulo();
        if (listaArticulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            System.out.println("Lista de artículos:");
            for (Articulo articulo : listaArticulos.getArrayList()) {
                System.out.println("El articulo es el siguiente:\n " + articulo.toString());
            }
        }
    }


    //Control de clientes:
    public void addCliente() {
        System.out.println("Ingrese los datos del nuevo cliente:");
        System.out.println("Mail: ");
        String mail = teclado.nextLine();
        ListaCliente<Cliente> listaCliente = datos.getListaCliente();
        Cliente clienteExistente = listaCliente.obtenerClientePorMail(mail);

        if (clienteExistente == null) {
            System.out.println("Nombre: ");
            String nombre = teclado.nextLine();
            System.out.println("NIF: ");
            String nif = teclado.nextLine();
            System.out.println("Domicilio: ");
            String domicilio = teclado.nextLine();

            System.out.println("¿Es un cliente Premium? (S/N)");
            String respuesta = teclado.nextLine().toUpperCase();
            Cliente cliente;
            if (respuesta.equals("S")) {
                cliente = new ClientePremium(mail, nombre, nif, domicilio);
            } else {
                cliente = new ClienteStandar(mail, nombre, nif, domicilio);
            }
            datos.getListaCliente().add(cliente);
            System.out.println("El Cliente creado contiene los siguientes datos:");
            System.out.println(cliente);
        } else {
            System.out.println("Este Mail ya existe.");
        }
    }

    public void mostarCliente() {
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
    }


    public void mostarClienteStandar() {
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
    }
    public void mostarClientePremium() {
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
    }

    // controlador Pedido
    public void addPedido() {
        System.out.println("Ingrese los datos del pedido");
        System.out.println("Codigo pedido:");
        int numeroPedido = teclado.nextInt();
        System.out.println("Cantidad:");
        int cantidad = teclado.nextInt();
        System.out.println("Codigo del articulo");
        int codigo = teclado.nextInt();
        teclado.nextLine();

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
                datos.getListaCliente().add(clientes);
                listaCliente = datos.getListaCliente();
                cliente = clientes;
                datos.setListaCliente(listaCliente);}
                Pedido pedido = new Pedido(numeroPedido, cantidad, articulo, cliente);
                datos.getListaPedido().add(pedido);
                System.out.println(pedido);
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
