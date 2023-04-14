package ElEscuadronDeLasConsultas.Vista;

import ElEscuadronDeLasConsultas.Controlador.Controlador;

import java.util.Scanner;
public class GestionOS {
    private Controlador controlador;
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
                case '1':
                    gestionarArticulos();
                    break;
                case '2':
                    gestionarCliente();
                    break;
                case '3':
                    gestionarPedido();
                    break;
                case '0':
                    salir = true;
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
                case '1':
                    controlador.addArticulo();
                    break;
                case '2':
                    controlador.mostrarArticulo();
                    break;
                case '0':
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
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
                case '1':
                    controlador.addCliente();
                    break;
                case '2':
                    controlador.mostarCliente();
                    break;
                case '3':
                    controlador.mostarClienteStandar();
                    break;
                case '4':
                    controlador.mostarClientePremium();
                    break;
                case '0':
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
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
                case '1':
                    controlador.addPedido();
                    break;
                case '2':
                    controlador.eliminarPedido();
                    break;
                case '3':
                    controlador.mostrarPedidoPendiente();
                    break;
                case '4':
                    controlador.mostrarPedidoEnviado();
                    break;
                case '0':
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!salir);
    }
}





