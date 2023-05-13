package Modelo;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "cliente")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoCliente", discriminatorType = DiscriminatorType.STRING)
public abstract class Cliente {

    //Atributos
    @Id
    private String mail;
    private String nombre;
    private String nif;
    private String domicilio;
    @Column(name = "tipoCliente", insertable = false, updatable = false)
    private String tipoCliente;

    //Atributos adicionales

    //constructor generico
    public Cliente(String mail, String nombre, String nif, String domicilio) {
        this.mail = mail;
        this.nombre = nombre;
        this.nif = nif;
        this.domicilio = domicilio;
    }

    public Cliente() {
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

    public abstract double calcAnual();

    public abstract double descuentoEnv();

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return  "\nNombre:             " + nombre +
                "\nMail:               " + mail +
                "\nNif:                " + nif +
                "\nDomicilio:          " + domicilio +
                "\nTipo de Cliente:    " + tipoCliente +
                "\nCuota anual:        " + df.format(calcAnual()) + "€" +
                "\nDescuento en envío: " + df.format((descuentoEnv() * 100)) + "%" +
                "\n";
    }
}