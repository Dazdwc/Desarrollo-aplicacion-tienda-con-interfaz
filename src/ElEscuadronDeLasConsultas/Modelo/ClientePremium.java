package ElEscuadronDeLasConsultas.Modelo;

public class ClientePremium extends Cliente {

    //Atributos
    private double descuento;
    private double cuota;

    public ClientePremium(String mail, String nombre, String nif, String domicilio) {
        super(mail, nombre, nif, domicilio);
        this.descuento = descuentoEnv();
        this.cuota = calcAnual();
    }
//getters and setters
    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    //Metodos abstractos

    public String tipoCliente() {
        return "Premium" ;
    }
    public double calcAnual() {

        return 30;
    }
    public double descuentoEnv() {

        return 0.2;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nPor ser cliente premiun obtienes las siguientes ventajas:" +
                "\nDescuento en envios: " + (descuentoEnv() * 100) + "%" +
                "\nSu cuota anual es de:" + calcAnual() + "€";
    }






}
