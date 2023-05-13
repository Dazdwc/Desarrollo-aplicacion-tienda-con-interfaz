package Modelo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArticuloDAOImp implements ArticuloDAO{
    private EntityManager em;

    public ArticuloDAOImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Articulo articulo) {
        em.getTransaction().begin();
        em.persist(articulo);
        em.getTransaction().commit();
    }

    @Override
    public Articulo read(String codigoArticulo) {
        return em.find(Articulo.class, codigoArticulo);
    }

    @Override
    public void update(Articulo articulo) {
        em.getTransaction().begin();
        em.merge(articulo);
        em.getTransaction().commit();
    }

    @Override
    public void delete(String codigoArticulo) {
        Articulo articulo = em.find(Articulo.class, codigoArticulo);
        if (articulo != null) {
            em.getTransaction().begin();
            em.remove(articulo);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Articulo> getAll() {
        TypedQuery<Articulo> query = em.createQuery("SELECT a FROM Articulo a", Articulo.class);
        return query.getResultList();
    }

    @Override
    public boolean existeArticulo(String codigoArticulo) {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Articulo c WHERE c.codigoArticulo = :codigoArticulo", Long.class);
            query.setParameter("codigoArticulo", codigoArticulo);
            Long count = query.getSingleResult();
            return count > 0;

    }

    public Articulo getArticulo(String codigoArticulo) {
        return em.find(Articulo.class, codigoArticulo);
    }



}
