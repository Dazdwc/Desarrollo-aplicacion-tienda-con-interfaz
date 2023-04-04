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
        lista.add("Element 1");
        assertEquals(1, lista.getSize());
        assertFalse(lista.isEmpty());
    }

    @Test
    void addMasElemento() {
        lista.add("Element 1");
        lista.add("Element 2");
        lista.add("Element 3");
        assertEquals(3, lista.getSize());
        assertFalse(lista.isEmpty());
    }

    @Test
    void BorradoElementos() {
        lista.add("Element 1");
        lista.add("Element 2");
        lista.borrar("Element 2");
        assertEquals(1, lista.getSize());
        assertFalse(lista.isEmpty());
        assertEquals("Element 1", lista.getAt(0));
    }

    @Test
    void RecorridogetAt() {
        lista.add("Element 1");
        lista.add("Element 2");
        assertEquals("Element 1", lista.getAt(0));
        assertEquals("Element 2", lista.getAt(1));
    }

    @Test
    void Limplieza() {
        lista.add("Element 1");
        lista.add("Element 2");
        lista.clear();
        assertEquals(0, lista.getSize());
        assertTrue(lista.isEmpty());
    }

    @Test
    void PruebaArrays() {
        lista.add("Element 1");
        lista.add("Element 2");
        ArrayList<String> arrList = lista.getArrayList();
        assertEquals(2, arrList.size());
        assertEquals("Element 1", arrList.get(0));
        assertEquals("Element 2", arrList.get(1));
    }

    @Test
    void ProbarVacio() {
        assertTrue(lista.isEmpty());
        lista.add("Element 1");
        assertFalse(lista.isEmpty());
    }

}