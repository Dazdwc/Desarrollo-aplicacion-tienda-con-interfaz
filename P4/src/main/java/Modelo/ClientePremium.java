package Modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Premium")
public class ClientePremium extends Cliente {

    //Atributos
    private double descuentoEnv;
    private double calcAnual;

    public ClientePremium() {
        super();
    }

    public ClientePremium(String mail, String nombre, String nif, String domicilio) {
        super(mail, nombre, nif, domicilio);
        this.descuentoEnv = descuentoEnv();
        this.calcAnual = calcAnual();
    }

    //getters and setters
    public double getDescuentoEnv() {
        return descuentoEnv;
    }

    public void setDescuentoEnv(double descuentoEnv) {
        this.descuentoEnv = descuentoEnv;
    }

    public double getCalcAnual() {
        return calcAnual;
    }

    public void setCalcAnual(double calcAnual) {
        this.calcAnual = calcAnual;
    }

    //Metodos abstractos

    public String tipoCliente() {
        return "Premium";
    }

    public double calcAnual() {

        return 30;
    }

    public double descuentoEnv() {

        return 0.2;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}