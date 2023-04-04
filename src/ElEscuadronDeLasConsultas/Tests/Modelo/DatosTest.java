package ElEscuadronDeLasConsultas.Tests.Modelo;
import ElEscuadronDeLasConsultas.Modelo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatosTest {

    private Datos datos;

    @BeforeEach
    void crearDatos() {
        datos = new Datos();
    }

    @Test
    void getListaArticuloTest() {
        assertNotNull(datos.getListaArticulo());
    }

    @Test
    void setListaArticuloTest() {
        ListaArticulo listaArticulo = new ListaArticulo();
        datos.setListaArticulo(listaArticulo);
        assertEquals(listaArticulo, datos.getListaArticulo());
    }

    @Test
    void getListaClienteTest() {
        assertNotNull(datos.getListaCliente());
    }

    @Test
    void setListaClienteTes() {
        ListaCliente listaCliente = new ListaCliente();
        datos.setListaCliente(listaCliente);
        assertEquals(listaCliente, datos.getListaCliente());
    }

    @Test
    void getListaPedidoTest() {
        assertNotNull(datos.getListaPedido());
    }

    @Test
    void setListaPedidoTest() {
        ListaPedido listaPedido = new ListaPedido();
        datos.setListaPedido(listaPedido);
        assertEquals(listaPedido, datos.getListaPedido());
    }

}