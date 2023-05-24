package com.example.proyecto_cuatro.Modelo;

import org.hibernate.HibernateException;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Pedido> getPedidosPorEstado(boolean enviado) {
        return mostrarPedidosPorEstado(enviado);
    }

    @Override
    public List<Pedido> mostrarPedidoFiltrado(String correo) {
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p WHERE p.cliente.mail = :correo", Pedido.class);
        query.setParameter("correo", correo);
        List<Pedido> pedidos = query.getResultList();

        return pedidos;
    }
    public String deleteIfNotSent(int numeroPedido) {
        Pedido pedido = getPedido(numeroPedido);  // Usa getPedido() en lugar de getPedidoPorNumero()
        if (pedido == null) {
            return "NO_EXISTE";
        } else if (pedido.pedidoEnviado()) {  // Usa pedidoEnviado() en lugar de isEnviado()
            return "ENVIADO";
        } else {
            delete(pedido);  // Usa delete() con el objeto pedido en lugar de deletePedido()
            return "BORRADO";
        }
    }

    @Override
    public List<Pedido> getPedidos() {
        // Crear la consulta
        Query query = em.createQuery("SELECT p FROM Pedido p", Pedido.class);

        // Ejecutar la consulta y obtener los resultados
        List<Pedido> pedidos = query.getResultList();

        // Devolver los pedidos
        return pedidos;
    }
}