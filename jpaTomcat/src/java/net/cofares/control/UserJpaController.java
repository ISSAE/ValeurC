/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cofares.control;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import net.cofares.Group1;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import net.cofares.User;
import net.cofares.control.exceptions.NonexistentEntityException;
import net.cofares.control.exceptions.PreexistingEntityException;

/**
 *
 * @author Pascal Fares <pfares@cofares.net>
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, Exception {
        if (user.getGroup1List() == null) {
            user.setGroup1List(new ArrayList<Group1>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Group1> attachedGroup1List = new ArrayList<Group1>();
            for (Group1 group1ListGroup1ToAttach : user.getGroup1List()) {
                group1ListGroup1ToAttach = em.getReference(group1ListGroup1ToAttach.getClass(), group1ListGroup1ToAttach.getGroupname());
                attachedGroup1List.add(group1ListGroup1ToAttach);
            }
            user.setGroup1List(attachedGroup1List);
            em.persist(user);
            for (Group1 group1ListGroup1 : user.getGroup1List()) {
                group1ListGroup1.getUserList().add(user);
                group1ListGroup1 = em.merge(group1ListGroup1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getUsername()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUsername());
            List<Group1> group1ListOld = persistentUser.getGroup1List();
            List<Group1> group1ListNew = user.getGroup1List();
            List<Group1> attachedGroup1ListNew = new ArrayList<Group1>();
            for (Group1 group1ListNewGroup1ToAttach : group1ListNew) {
                group1ListNewGroup1ToAttach = em.getReference(group1ListNewGroup1ToAttach.getClass(), group1ListNewGroup1ToAttach.getGroupname());
                attachedGroup1ListNew.add(group1ListNewGroup1ToAttach);
            }
            group1ListNew = attachedGroup1ListNew;
            user.setGroup1List(group1ListNew);
            user = em.merge(user);
            for (Group1 group1ListOldGroup1 : group1ListOld) {
                if (!group1ListNew.contains(group1ListOldGroup1)) {
                    group1ListOldGroup1.getUserList().remove(user);
                    group1ListOldGroup1 = em.merge(group1ListOldGroup1);
                }
            }
            for (Group1 group1ListNewGroup1 : group1ListNew) {
                if (!group1ListOld.contains(group1ListNewGroup1)) {
                    group1ListNewGroup1.getUserList().add(user);
                    group1ListNewGroup1 = em.merge(group1ListNewGroup1);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getUsername();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<Group1> group1List = user.getGroup1List();
            for (Group1 group1ListGroup1 : group1List) {
                group1ListGroup1.getUserList().remove(user);
                group1ListGroup1 = em.merge(group1ListGroup1);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
