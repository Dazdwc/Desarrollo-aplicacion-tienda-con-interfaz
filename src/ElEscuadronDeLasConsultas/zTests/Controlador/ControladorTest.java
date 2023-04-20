package ElEscuadronDeLasConsultas.zTests.Controlador;


import ElEscuadronDeLasConsultas.Modelo.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControladorTest {
        @Test
        void TestaddClientePremium() {

         ClientePremium cliente = new ClientePremium("mailsito", "pinipon", "12345678A", "123 Callepiruleta");
         String expected ="\nNombre:             pinipon" +
             "\nMail:               mailsito" +
             "\nNif:                12345678A" +
             "\nDomicilio:          123 Callepiruleta" +
             "\nTipo de Cliente:    Premium" +
             "\nCuota anual:        30" +"€" +
             "\nDescuento en envío: 20" +  "%" +
             "\n" +
                 "\nPor ser cliente premiun obtienes las siguientes ventajas:" +
                 "\nDescuento en envios: 20.0" + "%" +
                 "\nSu cuota anual es de:30.0" + "€";;
         assertEquals(expected, cliente.toString());
        }
         @Test
            void TestaddClienteStandar() {


        ClienteStandar cliente = new ClienteStandar("mailsito", "pinipon", "12345678A", "123 Callepiruleta");
        String expected ="\nNombre:             pinipon" +
                "\nMail:               mailsito" +
                "\nNif:                12345678A" +
                "\nDomicilio:          123 Callepiruleta" +
                "\nTipo de Cliente:    Standar" +
                "\nCuota anual:        0" +"€" +
                "\nDescuento en envío: 0" +  "%" +
                "\n";
        assertEquals(expected, cliente.toString());


    }
}
