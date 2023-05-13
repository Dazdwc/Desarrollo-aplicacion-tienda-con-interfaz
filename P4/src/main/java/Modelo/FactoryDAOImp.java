package Modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryDAOImp implements FactoryDAO {

    private EntityManagerFactory emf;

    public FactoryDAOImp() {
        emf = Persistence.createEntityManagerFactory("onlinestore");
    }

    @Override
    public ArticuloDAO createArticuloDAO() {
        EntityManager em = emf.createEntityManager();
        return new ArticuloDAOImp(em);
    }

    @Override
    public ClienteDAO createClienteDAO() {
        EntityManager em = emf.createEntityManager();
        return new ClienteDAOImp(em);
    }

    @Override
    public PedidoDAO createPedidoDAO() {
        EntityManager em = emf.createEntityManager();
        return new PedidoDAOImp(em);
    }
}