package ElEscuadronDeLasConsultas.Modelo;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Scanner;

public class Conexion {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("Introducir el usuario de la base de datos: ");
        String user = input.nextLine();
        System.out.println("Introducir el password del usuario: ");
        String pass = input.nextLine();
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elescuadrondelasconsultas", user, pass);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clientes");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("nombre"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
