package ElEscuadronDeLasConsultas.zTests.Modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ElEscuadronDeLasConsultas.Modelo.ClienteStandar;

public class ClienteStandarTest {

    @Test
    void tipoClienteTest() {
        ClienteStandar cliente = new ClienteStandar("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        assertEquals("Standar", cliente.tipoCliente());
    }

    @Test
    void testCalcAnual() {
        ClienteStandar cliente = new ClienteStandar("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        assertEquals(0, cliente.calcAnual());
    }

    @Test
    void testDescuentoEnv() {
        ClienteStandar cliente = new ClienteStandar("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        assertEquals(0, cliente.descuentoEnv());
    }

    @Test
    void testToString() {
        ClienteStandar cliente = new ClienteStandar("jdoe@example.com", "John Doe", "12345678A", "123 Main Street");
        String expected = "\nNombre:             John Doe\n"
                + "Mail:               jdoe@example.com\n"
                + "Nif:                12345678A\n"
                + "Domicilio:          123 Main Street\n"
                + "Tipo de Cliente:    Standar\n"
                + "Cuota anual:        0€\n"
                + "Descuento en envío: 0%\n";
        assertEquals(expected, cliente.toString());
    }

}