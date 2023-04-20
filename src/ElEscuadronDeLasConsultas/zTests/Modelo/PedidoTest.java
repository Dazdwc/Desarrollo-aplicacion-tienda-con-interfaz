package ElEscuadronDeLasConsultas.zTests.Modelo;

import ElEscuadronDeLasConsultas.Modelo.Articulo;
import ElEscuadronDeLasConsultas.Modelo.ClientePremium;
import ElEscuadronDeLasConsultas.Modelo.Pedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PedidoTest {

    @Test
    void testPruebaToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        ClientePremium cliente = new ClientePremium("Pimpamtoma@lacasitos.com", "Jonielserio", "12345678A", "unicornio 123");
        Articulo articulo = new Articulo("1", "descripcion", 10, 10, 3);
        Pedido pedido = new Pedido(1, 3,articulo, cliente);
        String expected = "\n Numero Pedido:       1" +
                "\n Realizado el:        " + LocalDateTime.now().format(formatter) +
                "\n Nombre cliente:      Jonielserio" +
                "\n Nif cliente:         12345678A" +
                "\n Codigo Articulo:     1" +
                "\n Descrición Articulo: descripcion" +
                "\n Cantidad:            3" +
                "\n Precio Articulo:     10.0" + "€" +
                "\n Precio envio:        8.0" + "€" +
                "\n Total Precio:        38.0" + "€" +
                "\n Pedido enviado:      No" +
                "\n\n Cancelable hasta:  " + LocalDateTime.now().plusMinutes(3).format(formatter) +
                "\n";
        assertEquals(expected, pedido.toString());
    }
}