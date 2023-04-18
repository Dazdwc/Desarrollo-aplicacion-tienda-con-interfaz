package ElEscuadronDeLasConsultas.Modelo;

public class FactoryMySQLDAO implements FactoryDAO {


        @Override
        public ArticuloDAO crearArticuloDAO() {
            return new ArticuloDAOimp();
        }
        @Override
        public ClienteDAO crearClienteDAO() {
            return new ClienteDAOimp();
        }
        @Override
        public PedidoDAO crearPedidoDAO() {
            return new PedidoDAOimp();
        }


}