package ElEscuadronDeLasConsultas.Modelo;

import java.sql.*;

public class Conexion {

    public static void main(String[] args) {

        String URL = "jdbc:mysql://localhost:3306/onlinestore";
        String USUARIO = "root";
        String CONTRASENA = "1234";


        try {
            Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            Statement statement = connection.createStatement();
            try {
            //String query = "INSERT INTO cliente(mail,nombre,nif,domicilio,premium,calcAnual,descuentoEnv) values ('perro93','9','2312733','hol84a',0,0,0);";
            String query = "DELETE FROM cliente";
            int rowAffected = statement.executeUpdate(query);
            System.out.println(rowAffected+ "filas afectada(s)");}
            catch (SQLException e){
                e.printStackTrace();
            }

            ResultSet resultSet = statement.executeQuery("SELECT * FROM cliente");
            while (resultSet.next()){
                System.out.println(resultSet.getString("mail" ) + " | " + resultSet.getString("nombre") );
            }
            connection.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}