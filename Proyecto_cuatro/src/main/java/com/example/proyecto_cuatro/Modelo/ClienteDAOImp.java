package com.example.proyecto_cuatro.Modelo;

import jakarta.persistence.*;
import java.util.List;

    public class ClienteDAOImp implements ClienteDAO {
        private EntityManager em;

        public ClienteDAOImp(EntityManager em) {
            this.em = em;
        }

        @Override
        public void create(Cliente cliente) {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        }

        @Override
        public Cliente read(String mail) {
            return em.find(Cliente.class, mail);
        }

        @Override
        public void update(Cliente cliente) {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        }

        @Override
        public void delete(String mail) {
            Cliente cliente = em.find(Cliente.class, mail);
            if (cliente != null) {
                em.getTransaction().begin();
                em.remove(cliente);
                em.getTransaction().commit();
            }
        }

        @Override
        public List<Cliente> getAll() {
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            return query.getResultList();
        }

        @Override
        public List<ClientePremium> getAllPremium() {
            TypedQuery<ClientePremium> query = em.createQuery("SELECT c FROM ClientePremium c", ClientePremium.class);
            return query.getResultList();
        }

        @Override
        public List<ClienteStandar> getAllStandar() {
            TypedQuery<ClienteStandar> query = em.createQuery("SELECT c FROM ClienteStandar c", ClienteStandar.class);
            return query.getResultList();
        }

        public boolean existeCliente(String mail) {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Cliente c WHERE c.mail = :mail", Long.class);
            query.setParameter("mail", mail);
            Long count = query.getSingleResult();
            return count > 0;
        }
        @Override
        public Cliente getCliente(String mail) {
            return em.find(Cliente.class, mail);
        }

    }