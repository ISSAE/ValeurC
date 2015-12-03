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
import net.cofares.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import net.cofares.Group1;
import net.cofares.control.exceptions.NonexistentEntityException;
import net.cofares.control.exceptions.PreexistingEntityException;

/**
 *
 * @author Pascal Fares <pfares@cofares.net>
 */
public class Group1JpaController implements Serializable {

    public Group1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Group1 group1) throws PreexistingEntityException, Exception {
        if (group1.getUserList() == null) {
            group1.setUserList(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<User> attachedUserList = new ArrayList<User>();
            for (User userListUserToAttach : group1.getUserList()) {
                userListUserToAttach = em.getReference(userListUserToAttach.getClass(), userListUserToAttach.getUsername());
                attachedUserList.add(userListUserToAttach);
            }
            group1.setUserList(attachedUserList);
            em.persist(group1);
            for (User userListUser : group1.getUserList()) {
                userListUser.getGroup1List().add(group1);
                userListUser = em.merge(userListUser);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGroup1(group1.getGroupname()) != null) {
                throw new PreexistingEntityException("Group1 " + group1 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Group1 group1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Group1 persistentGroup1 = em.find(Group1.class, group1.getGroupname());
            List<User> userListOld = persistentGroup1.getUserList();
            List<User> userListNew = group1.getUserList();
            List<User> attachedUserListNew = new ArrayList<User>();
            for (User userListNewUserToAttach : userListNew) {
                userListNewUserToAttach = em.getReference(userListNewUserToAttach.getClass(), userListNewUserToAttach.getUsername());
                attachedUserListNew.add(userListNewUserToAttach);
            }
            userListNew = attachedUserListNew;
            group1.setUserList(userListNew);
            group1 = em.merge(group1);
            for (User userListOldUser : userListOld) {
                if (!userListNew.contains(userListOldUser)) {
                    userListOldUser.getGroup1List().remove(group1);
                    userListOldUser = em.merge(userListOldUser);
                }
            }
            for (User userListNewUser : userListNew) {
                if (!userListOld.contains(userListNewUser)) {
                    userListNewUser.getGroup1List().add(group1);
                    userListNewUser = em.merge(userListNewUser);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = group1.getGroupname();
                if (findGroup1(id) == null) {
                    throw new NonexistentEntityException("The group1 with id " + id + " no longer exists.");
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
            Group1 group1;
            try {
                group1 = em.getReference(Group1.class, id);
                group1.getGroupname();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The group1 with id " + id + " no longer exists.", enfe);
            }
            List<User> userList = group1.getUserList();
            for (User userListUser : userList) {
                userListUser.getGroup1List().remove(group1);
                userListUser = em.merge(userListUser);
            }
            em.remove(group1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Group1> findGroup1Entities() {
        return findGroup1Entities(true, -1, -1);
    }

    public List<Group1> findGroup1Entities(int maxResults, int firstResult) {
        return findGroup1Entities(false, maxResults, firstResult);
    }

    private List<Group1> findGroup1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Group1.class));
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

    public Group1 findGroup1(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Group1.class, id);
        } finally {
            em.close();
        }
    }

    public int getGroup1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Group1> rt = cq.from(Group1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
