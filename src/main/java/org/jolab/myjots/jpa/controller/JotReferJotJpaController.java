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
import org.jolab.myjots.jpa.model.JotReferJot;
import org.jolab.myjots.jpa.model.JotReferJotPK;

/**
 *
 * @author jolab
 */
public class JotReferJotJpaController implements Serializable {

    public JotReferJotJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JotReferJot jotReferJot) throws PreexistingEntityException, Exception {
        if (jotReferJot.getJotReferJotPK() == null) {
            jotReferJot.setJotReferJotPK(new JotReferJotPK());
        }
        jotReferJot.getJotReferJotPK().setIdJotReferred(jotReferJot.getJot().getIdJot());
        jotReferJot.getJotReferJotPK().setIdJot(jotReferJot.getJot1().getIdJot());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jot jot = jotReferJot.getJot();
            if (jot != null) {
                jot = em.getReference(jot.getClass(), jot.getIdJot());
                jotReferJot.setJot(jot);
            }
            Jot jot1 = jotReferJot.getJot1();
            if (jot1 != null) {
                jot1 = em.getReference(jot1.getClass(), jot1.getIdJot());
                jotReferJot.setJot1(jot1);
            }
            em.persist(jotReferJot);
            if (jot != null) {
                jot.getJotReferJotList().add(jotReferJot);
                jot = em.merge(jot);
            }
            if (jot1 != null) {
                jot1.getJotReferJotList().add(jotReferJot);
                jot1 = em.merge(jot1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJotReferJot(jotReferJot.getJotReferJotPK()) != null) {
                throw new PreexistingEntityException("JotReferJot " + jotReferJot + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JotReferJot jotReferJot) throws NonexistentEntityException, Exception {
        jotReferJot.getJotReferJotPK().setIdJotReferred(jotReferJot.getJot().getIdJot());
        jotReferJot.getJotReferJotPK().setIdJot(jotReferJot.getJot1().getIdJot());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JotReferJot persistentJotReferJot = em.find(JotReferJot.class, jotReferJot.getJotReferJotPK());
            Jot jotOld = persistentJotReferJot.getJot();
            Jot jotNew = jotReferJot.getJot();
            Jot jot1Old = persistentJotReferJot.getJot1();
            Jot jot1New = jotReferJot.getJot1();
            if (jotNew != null) {
                jotNew = em.getReference(jotNew.getClass(), jotNew.getIdJot());
                jotReferJot.setJot(jotNew);
            }
            if (jot1New != null) {
                jot1New = em.getReference(jot1New.getClass(), jot1New.getIdJot());
                jotReferJot.setJot1(jot1New);
            }
            jotReferJot = em.merge(jotReferJot);
            if (jotOld != null && !jotOld.equals(jotNew)) {
                jotOld.getJotReferJotList().remove(jotReferJot);
                jotOld = em.merge(jotOld);
            }
            if (jotNew != null && !jotNew.equals(jotOld)) {
                jotNew.getJotReferJotList().add(jotReferJot);
                jotNew = em.merge(jotNew);
            }
            if (jot1Old != null && !jot1Old.equals(jot1New)) {
                jot1Old.getJotReferJotList().remove(jotReferJot);
                jot1Old = em.merge(jot1Old);
            }
            if (jot1New != null && !jot1New.equals(jot1Old)) {
                jot1New.getJotReferJotList().add(jotReferJot);
                jot1New = em.merge(jot1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JotReferJotPK id = jotReferJot.getJotReferJotPK();
                if (findJotReferJot(id) == null) {
                    throw new NonexistentEntityException("The jotReferJot with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JotReferJotPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JotReferJot jotReferJot;
            try {
                jotReferJot = em.getReference(JotReferJot.class, id);
                jotReferJot.getJotReferJotPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jotReferJot with id " + id + " no longer exists.", enfe);
            }
            Jot jot = jotReferJot.getJot();
            if (jot != null) {
                jot.getJotReferJotList().remove(jotReferJot);
                jot = em.merge(jot);
            }
            Jot jot1 = jotReferJot.getJot1();
            if (jot1 != null) {
                jot1.getJotReferJotList().remove(jotReferJot);
                jot1 = em.merge(jot1);
            }
            em.remove(jotReferJot);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JotReferJot> findJotReferJotEntities() {
        return findJotReferJotEntities(true, -1, -1);
    }

    public List<JotReferJot> findJotReferJotEntities(int maxResults, int firstResult) {
        return findJotReferJotEntities(false, maxResults, firstResult);
    }

    private List<JotReferJot> findJotReferJotEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JotReferJot.class));
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

    public JotReferJot findJotReferJot(JotReferJotPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JotReferJot.class, id);
        } finally {
            em.close();
        }
    }

    public int getJotReferJotCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JotReferJot> rt = cq.from(JotReferJot.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
