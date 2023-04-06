package ElEscuadronDeLasConsultas.zTests.Modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ElEscuadronDeLasConsultas.Modelo.ClientePremium;

public class ClientePremiumTest {

    @Test
    void testTipoCliente() {
        ClientePremium cliente = new ClientePremium("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        assertEquals("Premium", cliente.tipoCliente());
    }

    @Test
    void testCalcAnual() {
        ClientePremium cliente = new ClientePremium("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        assertEquals(30, cliente.calcAnual());
    }

    @Test
    void testDescuentoEnv() {
        ClientePremium cliente = new ClientePremium("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        assertEquals(0.2, cliente.descuentoEnv());
    }

    @Test
    void testToString() {
        ClientePremium cliente = new ClientePremium("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        String expected = "\nNombre:             John Doe\n"
                + "Mail:               jdoe@example.com\n"
                + "Nif:                12345678A\n"
                + "Domicilio:          123 Main Street\n"
                + "Tipo de Cliente:    Premium\n"
                + "Cuota anual:        30€\n"
                + "Descuento en envío: 20%\n"
                + "\nPor ser cliente premiun obtienes las siguientes ventajas:"
                + "\nDescuento en envios: 20.0%\n"
                + "Su cuota anual es de:30.0€";
        assertEquals(expected, cliente.toString());
    }
}