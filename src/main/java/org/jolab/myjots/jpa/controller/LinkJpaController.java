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
import org.jolab.myjots.jpa.model.Jot;
import org.jolab.myjots.jpa.model.Link;

/**
 *
 * @author jolab
 */
public class LinkJpaController implements Serializable {

    public LinkJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Link link) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jot idJot = link.getIdJot();
            if (idJot != null) {
                idJot = em.getReference(idJot.getClass(), idJot.getIdJot());
                link.setIdJot(idJot);
            }
            em.persist(link);
            if (idJot != null) {
                idJot.getLinkList().add(link);
                idJot = em.merge(idJot);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Link link) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Link persistentLink = em.find(Link.class, link.getIdLink());
            Jot idJotOld = persistentLink.getIdJot();
            Jot idJotNew = link.getIdJot();
            if (idJotNew != null) {
                idJotNew = em.getReference(idJotNew.getClass(), idJotNew.getIdJot());
                link.setIdJot(idJotNew);
            }
            link = em.merge(link);
            if (idJotOld != null && !idJotOld.equals(idJotNew)) {
                idJotOld.getLinkList().remove(link);
                idJotOld = em.merge(idJotOld);
            }
            if (idJotNew != null && !idJotNew.equals(idJotOld)) {
                idJotNew.getLinkList().add(link);
                idJotNew = em.merge(idJotNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = link.getIdLink();
                if (findLink(id) == null) {
                    throw new NonexistentEntityException("The link with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Link link;
            try {
                link = em.getReference(Link.class, id);
                link.getIdLink();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The link with id " + id + " no longer exists.", enfe);
            }
            Jot idJot = link.getIdJot();
            if (idJot != null) {
                idJot.getLinkList().remove(link);
                idJot = em.merge(idJot);
            }
            em.remove(link);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Link> findLinkEntities() {
        return findLinkEntities(true, -1, -1);
    }

    public List<Link> findLinkEntities(int maxResults, int firstResult) {
        return findLinkEntities(false, maxResults, firstResult);
    }

    private List<Link> findLinkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Link.class));
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

    public Link findLink(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Link.class, id);
        } finally {
            em.close();
        }
    }

    public int getLinkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Link> rt = cq.from(Link.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
