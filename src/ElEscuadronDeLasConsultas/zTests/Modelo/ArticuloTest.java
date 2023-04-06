package ElEscuadronDeLasConsultas.zTests.Modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ElEscuadronDeLasConsultas.Modelo.Articulo;

public class ArticuloTest {

    private Articulo articulo;

    @BeforeEach
    public void setUp() {
        articulo = new Articulo(1, "Artículo de prueba", 10.5f, 2.0, 30);
    }

    @Test
    public void testGetCodigoArticulo() {
        assertEquals(1, articulo.getCodigoArticulo());
    }

    @Test
    public void testSetCodigoArticulo() {
        articulo.setCodigoArticulo(2);
        assertEquals(2, articulo.getCodigoArticulo());
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Artículo de prueba", articulo.getDescripcion());
    }

    @Test
    public void testSetDescripcion() {
        articulo.setDescripcion("Nuevo artículo de prueba");
        assertEquals("Nuevo artículo de prueba", articulo.getDescripcion());
    }

    @Test
    public void testGetPvp() {
        assertEquals(10.5f, articulo.getPvp(), 0.01);
    }

    @Test
    public void testSetPvp() {
        articulo.setPvp(15.0f);
        assertEquals(15.0f, articulo.getPvp(), 0.01);
    }

    @Test
    public void testGetGastosEnvio() {
        assertEquals(2.0, articulo.getGastosEnvio(), 0.01);
    }

    @Test
    public void testSetGastosEnvio() {
        articulo.setGastosEnvio(3.0);
        assertEquals(3.0, articulo.getGastosEnvio(), 0.01);
    }

    @Test
    public void testGetPreparacionMin() {
        assertEquals(30, articulo.getPreparacionMin());
    }

    @Test
    public void testSetPreparacionMin() {
        articulo.setPreparacionMin(45);
        assertEquals(45, articulo.getPreparacionMin());
    }

    @Test
    public void testToString() {
        String expected = "\nCodigo Articulo:       1\nDescripción Articulo:  Artículo de prueba\nPrecio Articulo:       10.5\nGastos de Envio:       2.0\nPreparacion en Min:    30\n";
        assertEquals(expected, articulo.toString());
    }

}

