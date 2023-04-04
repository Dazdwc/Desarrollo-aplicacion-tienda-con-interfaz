package ElEscuadronDeLasConsultas.Tests.Modelo;
import ElEscuadronDeLasConsultas.Modelo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


class ListaTest {

    private Lista<String> lista;

    @BeforeEach
    void nuevaLista() {
        lista = new Lista<>();
    }

    @Test
    void detectarVacio() {
        assertEquals(0, lista.getSize());
    }

    @Test
    void aadElemento() {
        lista.add("añadimos");
        assertEquals(1, lista.getSize());
        assertFalse(lista.isEmpty());
    }

    @Test
    void addMasElemento() {
        lista.add("añado 1");
        lista.add("que sean 2");
        lista.add("venga ponme 3");
        assertEquals(3, lista.getSize());
        assertFalse(lista.isEmpty());
    }

    @Test
    void BorradoElementos() {
        lista.add("Esto se queda");
        lista.add("Esto se va");
        lista.borrar("Esto se va");
        assertEquals(1, lista.getSize());
        assertFalse(lista.isEmpty());
        assertEquals("Esto se queda", lista.getAt(0));
    }

    @Test
    void RecorridogetAt() {
        lista.add("Primer elemento");
        lista.add("Segundo elemento");
        assertEquals("Primer elemento", lista.getAt(0));
        assertEquals("Segundo elemento", lista.getAt(1));
    }

    @Test
    void Limplieza() {
        lista.add("Esto se va");
        lista.add("como su amor por ti");
        lista.clear();
        assertEquals(0, lista.getSize());
        assertTrue(lista.isEmpty());
    }

    @Test
    void PruebaArrays() {
        lista.add("testeando array");
        lista.add("segundo elemento");
        ArrayList<String> arrList = lista.getArrayList();
        assertEquals(2, arrList.size());
        assertEquals("testeando array", arrList.get(0));
        assertEquals("segundo elemento", arrList.get(1));
    }

    @Test
    void ProbarVacio() {
        assertTrue(lista.isEmpty());
        lista.add("esto ya no esta vacío");
        assertFalse(lista.isEmpty());
    }

}