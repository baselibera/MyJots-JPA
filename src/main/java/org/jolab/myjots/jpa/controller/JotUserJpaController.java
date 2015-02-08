/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jolab.myjots.jpa.controller.exceptions.NonexistentEntityException;
import org.jolab.myjots.jpa.controller.exceptions.PreexistingEntityException;
import org.jolab.myjots.jpa.model.Jot;
import org.jolab.myjots.jpa.model.JotUser;
import org.jolab.myjots.jpa.model.JotUserPK;
import org.jolab.myjots.jpa.model.User;

/**
 *
 * @author jolab
 */
public class JotUserJpaController implements Serializable {

    public JotUserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JotUser jotUser) throws PreexistingEntityException, Exception {
        if (jotUser.getJotUserPK() == null) {
            jotUser.setJotUserPK(new JotUserPK());
        }
        jotUser.getJotUserPK().setIdAppUser(jotUser.getUser().getIdAppUser());
        jotUser.getJotUserPK().setIdJot(jotUser.getJot().getIdJot());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jot jot = jotUser.getJot();
            if (jot != null) {
                jot = em.getReference(jot.getClass(), jot.getIdJot());
                jotUser.setJot(jot);
            }
            User user = jotUser.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getIdAppUser());
                jotUser.setUser(user);
            }
            em.persist(jotUser);
            if (jot != null) {
                jot.getJotUserList().add(jotUser);
                jot = em.merge(jot);
            }
            if (user != null) {
                user.getJotUserList().add(jotUser);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJotUser(jotUser.getJotUserPK()) != null) {
                throw new PreexistingEntityException("JotUser " + jotUser + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JotUser jotUser) throws NonexistentEntityException, Exception {
        jotUser.getJotUserPK().setIdAppUser(jotUser.getUser().getIdAppUser());
        jotUser.getJotUserPK().setIdJot(jotUser.getJot().getIdJot());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JotUser persistentJotUser = em.find(JotUser.class, jotUser.getJotUserPK());
            Jot jotOld = persistentJotUser.getJot();
            Jot jotNew = jotUser.getJot();
            User userOld = persistentJotUser.getUser();
            User userNew = jotUser.getUser();
            if (jotNew != null) {
                jotNew = em.getReference(jotNew.getClass(), jotNew.getIdJot());
                jotUser.setJot(jotNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getIdAppUser());
                jotUser.setUser(userNew);
            }
            jotUser = em.merge(jotUser);
            if (jotOld != null && !jotOld.equals(jotNew)) {
                jotOld.getJotUserList().remove(jotUser);
                jotOld = em.merge(jotOld);
            }
            if (jotNew != null && !jotNew.equals(jotOld)) {
                jotNew.getJotUserList().add(jotUser);
                jotNew = em.merge(jotNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getJotUserList().remove(jotUser);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getJotUserList().add(jotUser);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JotUserPK id = jotUser.getJotUserPK();
                if (findJotUser(id) == null) {
                    throw new NonexistentEntityException("The jotUser with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JotUserPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JotUser jotUser;
            try {
                jotUser = em.getReference(JotUser.class, id);
                jotUser.getJotUserPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jotUser with id " + id + " no longer exists.", enfe);
            }
            Jot jot = jotUser.getJot();
            if (jot != null) {
                jot.getJotUserList().remove(jotUser);
                jot = em.merge(jot);
            }
            User user = jotUser.getUser();
            if (user != null) {
                user.getJotUserList().remove(jotUser);
                user = em.merge(user);
            }
            em.remove(jotUser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JotUser> findJotUserEntities() {
        return findJotUserEntities(true, -1, -1);
    }

    public List<JotUser> findJotUserEntities(int maxResults, int firstResult) {
        return findJotUserEntities(false, maxResults, firstResult);
    }

    private List<JotUser> findJotUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JotUser.class));
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

    public JotUser findJotUser(JotUserPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JotUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getJotUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JotUser> rt = cq.from(JotUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
