package com.example.proyecto_cuatro.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name="articulo")
public class
Articulo {

    //Atributos
    @Id
    @Column(name = "codigoArticulo")
    private String codigoArticulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "pvp")
    private float pvp;
    @Column(name = "gastosEnvio")
    private double gastosEnvio;
    @Column(name = "preparacionMin")
    private int preparacionMin;

    public Articulo() {
    }

    //constructor
    public Articulo(String codigoArticulo, String descripcion, float pvp, double gastosEnvio, int preparacionMin) {
        this.codigoArticulo = codigoArticulo;
        this.descripcion = descripcion;
        this.pvp = pvp;
        this.gastosEnvio = gastosEnvio;
        this.preparacionMin = preparacionMin;
    }

    //getters and setters


    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPvp() {
        return pvp;
    }

    public void setPvp(float pvp) {
        this.pvp = pvp;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public int getPreparacionMin() { return preparacionMin;
    }

    public void setPreparacionMin(int preparacionMin) {
        this.preparacionMin = preparacionMin;
    }

    // ToString


    @Override
    public String toString() {
        return  "\nCodigo Articulo:       " + codigoArticulo +
                "\nDescripción Articulo:  " + descripcion +
                "\nPrecio Articulo:       " + pvp +
                "\nGastos de Envio:       " + gastosEnvio +
                "\nPreparacion en Min:    " + preparacionMin +
                "\n";
    }
}
