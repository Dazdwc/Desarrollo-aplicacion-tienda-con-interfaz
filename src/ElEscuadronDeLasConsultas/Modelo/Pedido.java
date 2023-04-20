package ElEscuadronDeLasConsultas.Modelo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    //Atributos
    private int numeroPedido;
    private int cantidad;
    private LocalDateTime fechaHora = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private Articulo articulo;
    private Cliente cliente;

    public Pedido(int numeroPedido, int cantidad, Articulo articulo, Cliente cliente) {
        this.numeroPedido = numeroPedido;
        this.cantidad = cantidad;
        this.articulo = articulo;
        this.cliente = cliente;
    }
    public Pedido(int numeroPedido, int cantidad,LocalDateTime fecha,Articulo articulo, Cliente cliente) {
        this.numeroPedido = numeroPedido;
        this.cantidad = cantidad;
        this.fechaHora = fecha;
        this.articulo = articulo;
        this.cliente = cliente;
    }


    public Pedido(int numeroPedido, int cantidad, Articulo articulo, Cliente cliente, LocalDateTime fechaConver) {
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaHora() {

        return this.fechaHora.format(formatter);
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return  "\n Numero Pedido:       " + numeroPedido +
                "\n Realizado el:        " + getFechaHora() +
                "\n Nombre cliente:      " + cliente.getNombre() +
                "\n Nif cliente:         " + cliente.getNif() +
                "\n Codigo Articulo:     " + articulo.getCodigoArticulo() +
                "\n Descrición Articulo: " + articulo.getDescripcion() +
                "\n Cantidad:            " + cantidad +
                "\n Precio Articulo:     " + articulo.getPvp() + "€" +
                "\n Precio envio:        " + precioEnvio() + "€" +
                "\n Total Precio:        " + PrecioTotal() + "€" +
                "\n Pedido enviado:      " + (pedidoEnviado() ? "Si" : "No") +
                "\n\n Cancelable hasta:  " + fechaHora.plusMinutes(articulo.getPreparacionMin()).format(formatter)+
                "\n";
    }

    public boolean pedidoEnviado() {
        LocalDateTime horaPreparacion = fechaHora.plusMinutes(articulo.getPreparacionMin());
        LocalDateTime horaActual = LocalDateTime.now();
        return horaActual.isAfter(horaPreparacion);
    }



        public double precioEnvio() {
                double descuento = cliente.descuentoEnv();
            return articulo.getGastosEnvio() * (1 - descuento);
        }

        public double PrecioTotal(){
            return cantidad * articulo.getPvp() + precioEnvio();
        }

}
