package Modelo;

import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PedidoDAOImp implements PedidoDAO {
    private EntityManager em;

    public PedidoDAOImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Pedido pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    @Override
    public Pedido read(int numeroPedido) {
        return em.find(Pedido.class, numeroPedido);
    }

    @Override
    public void update(Pedido pedido) {
        em.getTransaction().begin();
        em.merge(pedido);
        em.getTransaction().commit();
    }
    @Override
    public Pedido getPedido(int numeroPedido) {
        try {
            Pedido pedido = em.find(Pedido.class, numeroPedido);
            return pedido;
        } catch (Exception e) {
            throw new HibernateException("Error al buscar el pedido: " + e.getMessage(), e);
        }
    }
    @Override
    public void delete(Pedido pedido) {
        try {
            em.getTransaction().begin();
            em.remove(pedido);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Pedido> getAll() {
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        return query.getResultList();
    }
    @Override
    public boolean existePedido(int numeroPedido) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Pedido c WHERE c.numeroPedido = :numeroPedido", Long.class);
        query.setParameter("numeroPedido", numeroPedido);
        Long count = query.getSingleResult();
        return count > 0;
    }

    /** Así gestionamos los pedidos por estado**/
    private List<Pedido> mostrarPedidosPorEstado(boolean enviado) {
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        List<Pedido> pedidos = query.getResultList();
        List<Pedido> pedidosFiltrados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.pedidoEnviado() == enviado) {
                pedidosFiltrados.add(pedido);
            }
        }

        return pedidosFiltrados;
    }
    @Override
    public void mostrarPedidoPendiente() {
        List<Pedido> pedidos = mostrarPedidosPorEstado(false);
        if (pedidos.isEmpty()) {
            System.out.println("No existen pedidos que mostrar");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }

    @Override
    public void mostrarPedidoEnviado() {
        List<Pedido> pedidos = mostrarPedidosPorEstado(true);
        if (pedidos.isEmpty()) {
            System.out.println("No existen pedidos que mostrar");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }

    @Override
    public void mostrarPedidoEnviadoFiltrado(String correo) {
        List<Pedido> pedidos = mostrarPedidosPorEstado(true);
        boolean found = false;
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getMail().equals(correo)) {
                System.out.println(pedido);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No existen pedidos que mostrar");
        }
    }

    @Override
    public void mostrarPedidoPendienteFiltrado(String correo) {
        List<Pedido> pedidos = mostrarPedidosPorEstado(false);
        boolean found = false;
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getMail().equals(correo)) {
                System.out.println(pedido);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No existen pedidos que mostrar");
        }
    }
}