package ElEscuadronDeLasConsultas.Modelo;

import java.text.DecimalFormat;

public abstract class Cliente {

    //Atributos
    private String mail;
    private String nombre;
    private String nif;
    private String domicilio;

    //Atributos adicionales

    //constructor generico
    public Cliente(String mail, String nombre, String nif, String domicilio) {
        this.mail = mail;
        this.nombre = nombre;
        this.nif = nif;
        this.domicilio = domicilio;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    //METODOS ABSTRACTOS
    public abstract String tipoCliente();

    public abstract double calcAnual();

    public abstract double descuentoEnv();

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return  "\nNombre:             " + nombre +
                "\nMail:               " + mail +
                "\nNif:                " + nif +
                "\nDomicilio:          " + domicilio +
                "\nTipo de Cliente:    " + tipoCliente() +
                "\nCuota anual:        " + df.format(calcAnual()) + "€" +
                "\nDescuento en envío: " + df.format((descuentoEnv() * 100)) + "%" +
                "\n";
    }
}