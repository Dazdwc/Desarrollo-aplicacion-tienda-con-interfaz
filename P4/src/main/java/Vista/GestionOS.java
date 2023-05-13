package Vista;

import Controlador.Controlador;

import java.util.Scanner;
public class GestionOS {
    private final Controlador controlador;
    Scanner teclado = new Scanner(System.in);

    public GestionOS() {
        controlador = new Controlador();
    }

    char pedirOpcion() {
        String resp;
        System.out.println("Elige un numero de la opción del menú: ");
        resp = teclado.nextLine();
        if (resp.isEmpty()) {
            resp = " ";
        }
        return resp.charAt(0);
    }

    public void inicio() {
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Gestión Articulos");
            System.out.println("2. Gestión Clientes");
            System.out.println("3. Gestión Pedidos");
            System.out.println("0. Salir");
            opcio = pedirOpcion();
            switch (opcio) {
                case '1' -> gestionarArticulos();
                case '2' -> gestionarCliente();
                case '3' -> gestionarPedido();
                case '0' -> salir = true;
            }
        } while (!salir);
    }


    public void gestionarArticulos() {
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Crear Articulo");
            System.out.println("2. Mostrar Articulos");
            System.out.println("0. Volver");
            opcio = pedirOpcion();
            switch (opcio) {
                case '1' -> controlador.addArticulo();
                case '2' -> controlador.mostrarArticulos();
                case '0' -> salir = true;
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!salir);
    }

    public void gestionarCliente() {
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Crear cliente");
            System.out.println("2. Mostrar todos los clientes");
            System.out.println("3. Mostrar clientes standar");
            System.out.println("4. Mostrar clientes premium");
            System.out.println("0. Volver");
            opcio = pedirOpcion();
            switch (opcio) {
                case '1' -> controlador.addCliente();
                case '2' -> controlador.mostarCliente();
                case '3' -> controlador.mostarClienteStandar();
                case '4' -> controlador.mostarClientePremium();
                case '0' -> salir = true;
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!salir);
    }

    public void gestionarPedido() {
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Crear Pedido");
            System.out.println("2. Eliminar Pedido");
            System.out.println("3. Mostrar Pedidos Pendientes");
            System.out.println("4. Mostrar Pedidos Enviados");
            System.out.println("0. Volver");
            opcio = pedirOpcion();
            switch (opcio) {
                case '1' -> controlador.addPedido();
                case '2' -> controlador.eliminarPedido();
                case '3' -> controlador.mostrarPedidoPendiente();
                case '4' -> controlador.mostrarPedidoEnviado();
                case '0' -> salir = true;
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!salir);
    }
}

