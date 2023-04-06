package ElEscuadronDeLasConsultas.zTests.Modelo;
import ElEscuadronDeLasConsultas.Modelo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListaArticuloTest {
    private ListaArticulo<Articulo> listaArticulo;
    @BeforeEach
    void CrearLista() {
        listaArticulo = new ListaArticulo<>();
    }

    @Test
    void DetectarNullListavacia() {
        assertNull(listaArticulo.obtenerCodigoArticulo(1));
    }


}