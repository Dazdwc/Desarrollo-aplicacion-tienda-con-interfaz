package ElEscuadronDeLasConsultas.Vista;

import java.sql.SQLException;

public class OnlineStore {

    public static void main (String[] args) {
        GestionOS gestion = new GestionOS();
        try {
            gestion.inicio();
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }
}

