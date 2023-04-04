package ElEscuadronDeLasConsultas.Modelo;

public class Articulo {

    //Atributos
    private int codigoArticulo;
    private String descripcion;
    private float pvp;
    private double gastosEnvio;
    private int preparacionMin;


//constructor
    public Articulo(int codigoArticulo, String descripcion, float pvp, double gastosEnvio, int preparacionMin) {
        this.codigoArticulo = codigoArticulo;
        this.descripcion = descripcion;
        this.pvp = pvp;
        this.gastosEnvio = gastosEnvio;
        this.preparacionMin = preparacionMin;
    }

    //getters and setters


    public int getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(int codigoArticulo) {
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
