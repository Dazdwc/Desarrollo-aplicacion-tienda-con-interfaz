package com.example.proyecto_cuatro.Modelo;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Standar")
public class ClienteStandar extends Cliente {
    //Atributos
    private double descuentoEnv;
    private double calcAnual;
    //Constructores
    public ClienteStandar(String mail, String nombre, String nif, String domicilio) {
        super(mail, nombre, nif, domicilio);
        this.descuentoEnv = descuentoEnv();
        this.calcAnual = calcAnual();
    }

    public ClienteStandar(){
        super();
    }

    // Métodos abstractos
    public double calcAnual() {
        return 0.0;
    }

    public double descuentoEnv() {
        return 0.0;
    }

    //toString
    @Override
    public String toString() {
        return super.toString();
    }
}