package ElEscuadronDeLasConsultas.Modelo;


public interface FactoryDAO {
    ArticuloDAO crearArticuloDAO();
    ClienteDAO crearClienteDAO();
    PedidoDAO crearPedidoDAO();

}

