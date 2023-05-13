package ORM;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * La clase FactoryUtil proporciona una utilidad para crear y acceder a la instancia EntityManager para nuestra base de datos
 */
public class FactoryUtil {
    /**
     * La clase tiene un método estático llamado "getEntityManager" que devuelve una instancia de EntityManager para la tienda en línea.
     */
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    /**
     * Construye la instancia EntityManagerFactory para para onlineStore.
     *
     * @return La instancia EntityManagerFactory.
     */
    private static EntityManagerFactory buildEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("onlineStore");
    }

    /**
     * Obtiene la instancia EntityManager para onlineStore.
     *
     * @return La instancia EntityManager.
     */
    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
