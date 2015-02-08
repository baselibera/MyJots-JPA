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
import org.jolab.myjots.jpa.model.JotHistory;
import org.jolab.myjots.jpa.model.JotHistoryPK;

/**
 *
 * @author jolab
 */
public class JotHistoryJpaController implements Serializable {

    public JotHistoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JotHistory jotHistory) throws PreexistingEntityException, Exception {
        if (jotHistory.getJotHistoryPK() == null) {
            jotHistory.setJotHistoryPK(new JotHistoryPK());
        }
        jotHistory.getJotHistoryPK().setIdJot(jotHistory.getJot().getIdJot());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jot jot = jotHistory.getJot();
            if (jot != null) {
                jot = em.getReference(jot.getClass(), jot.getIdJot());
                jotHistory.setJot(jot);
            }
            em.persist(jotHistory);
            if (jot != null) {
                jot.getJotHistoryList().add(jotHistory);
                jot = em.merge(jot);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJotHistory(jotHistory.getJotHistoryPK()) != null) {
                throw new PreexistingEntityException("JotHistory " + jotHistory + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JotHistory jotHistory) throws NonexistentEntityException, Exception {
        jotHistory.getJotHistoryPK().setIdJot(jotHistory.getJot().getIdJot());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JotHistory persistentJotHistory = em.find(JotHistory.class, jotHistory.getJotHistoryPK());
            Jot jotOld = persistentJotHistory.getJot();
            Jot jotNew = jotHistory.getJot();
            if (jotNew != null) {
                jotNew = em.getReference(jotNew.getClass(), jotNew.getIdJot());
                jotHistory.setJot(jotNew);
            }
            jotHistory = em.merge(jotHistory);
            if (jotOld != null && !jotOld.equals(jotNew)) {
                jotOld.getJotHistoryList().remove(jotHistory);
                jotOld = em.merge(jotOld);
            }
            if (jotNew != null && !jotNew.equals(jotOld)) {
                jotNew.getJotHistoryList().add(jotHistory);
                jotNew = em.merge(jotNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JotHistoryPK id = jotHistory.getJotHistoryPK();
                if (findJotHistory(id) == null) {
                    throw new NonexistentEntityException("The jotHistory with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JotHistoryPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JotHistory jotHistory;
            try {
                jotHistory = em.getReference(JotHistory.class, id);
                jotHistory.getJotHistoryPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jotHistory with id " + id + " no longer exists.", enfe);
            }
            Jot jot = jotHistory.getJot();
            if (jot != null) {
                jot.getJotHistoryList().remove(jotHistory);
                jot = em.merge(jot);
            }
            em.remove(jotHistory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JotHistory> findJotHistoryEntities() {
        return findJotHistoryEntities(true, -1, -1);
    }

    public List<JotHistory> findJotHistoryEntities(int maxResults, int firstResult) {
        return findJotHistoryEntities(false, maxResults, firstResult);
    }

    private List<JotHistory> findJotHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JotHistory.class));
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

    public JotHistory findJotHistory(JotHistoryPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JotHistory.class, id);
        } finally {
            em.close();
        }
    }

    public int getJotHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JotHistory> rt = cq.from(JotHistory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
