/*
 * Cette classe abstraite permet de préparer les accès JPA 
 * dans le cadre d'applications java en utilisant juste l'interface
 * JPA eclipselink
 */
package net.cofares.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pascal Fares <pfares@cofares.net>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //protected abstract EntityManager getEntityManager();
    //private EntityManagerFactory emf = null;
    protected abstract EntityManagerFactory getEntityManagerFactory();

    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public void create(T entity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(T entity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(T entity) {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(entity));
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public T find(Object id) {
        EntityManager em = null;
        try {
            em = getEntityManager();

            T res = em.find(entityClass, id);

            return res;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<T> findAll() {
        EntityManager em = null;
        try {

            em = getEntityManager();

            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            List<T> res = em.createQuery(cq).getResultList();

            return res;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<T> findRange(int[] range) {
        EntityManager em = null;
        try {

            em = getEntityManager();

            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            javax.persistence.Query q = em.createQuery(cq);
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
            List<T> res = q.getResultList();

            return res;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int count() {
        EntityManager em = null;
        try {
            em = getEntityManager();

            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
            cq.select(em.getCriteriaBuilder().count(rt));
            javax.persistence.Query q = em.createQuery(cq);
            int res = ((Long) q.getSingleResult()).intValue();

            return res;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
