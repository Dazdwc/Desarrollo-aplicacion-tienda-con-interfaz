package ElEscuadronDeLasConsultas.Modelo;

public class ClienteStandar extends Cliente {

    //Constructores
    public ClienteStandar(String mail, String nombre, String nif, String domicilio) {
        super(mail, nombre, nif, domicilio);
    }


    // Metodos abstractos
    public String tipoCliente() {
        return "Standar" ;
    }


    public double calcAnual() {
        return 0;
    }


    public double descuentoEnv() {

        return 0;
    }

    //toString
    @Override
    public String toString() {
        return super.toString();
    }




}
