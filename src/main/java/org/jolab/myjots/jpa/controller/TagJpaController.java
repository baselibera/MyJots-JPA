/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jolab.myjots.jpa.model.Jot;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jolab.myjots.jpa.controller.exceptions.NonexistentEntityException;
import org.jolab.myjots.jpa.model.Tag;

/**
 *
 * @author jolab
 */
public class TagJpaController implements Serializable {

    public TagJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tag tag) {
        if (tag.getJotList() == null) {
            tag.setJotList(new ArrayList<Jot>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Jot> attachedJotList = new ArrayList<Jot>();
            for (Jot jotListJotToAttach : tag.getJotList()) {
                jotListJotToAttach = em.getReference(jotListJotToAttach.getClass(), jotListJotToAttach.getIdJot());
                attachedJotList.add(jotListJotToAttach);
            }
            tag.setJotList(attachedJotList);
            em.persist(tag);
            for (Jot jotListJot : tag.getJotList()) {
                jotListJot.getTagList().add(tag);
                jotListJot = em.merge(jotListJot);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tag tag) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag persistentTag = em.find(Tag.class, tag.getIdTag());
            List<Jot> jotListOld = persistentTag.getJotList();
            List<Jot> jotListNew = tag.getJotList();
            List<Jot> attachedJotListNew = new ArrayList<Jot>();
            for (Jot jotListNewJotToAttach : jotListNew) {
                jotListNewJotToAttach = em.getReference(jotListNewJotToAttach.getClass(), jotListNewJotToAttach.getIdJot());
                attachedJotListNew.add(jotListNewJotToAttach);
            }
            jotListNew = attachedJotListNew;
            tag.setJotList(jotListNew);
            tag = em.merge(tag);
            for (Jot jotListOldJot : jotListOld) {
                if (!jotListNew.contains(jotListOldJot)) {
                    jotListOldJot.getTagList().remove(tag);
                    jotListOldJot = em.merge(jotListOldJot);
                }
            }
            for (Jot jotListNewJot : jotListNew) {
                if (!jotListOld.contains(jotListNewJot)) {
                    jotListNewJot.getTagList().add(tag);
                    jotListNewJot = em.merge(jotListNewJot);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tag.getIdTag();
                if (findTag(id) == null) {
                    throw new NonexistentEntityException("The tag with id " + id + " no longer exists.");
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
            Tag tag;
            try {
                tag = em.getReference(Tag.class, id);
                tag.getIdTag();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tag with id " + id + " no longer exists.", enfe);
            }
            List<Jot> jotList = tag.getJotList();
            for (Jot jotListJot : jotList) {
                jotListJot.getTagList().remove(tag);
                jotListJot = em.merge(jotListJot);
            }
            em.remove(tag);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tag> findTagEntities() {
        return findTagEntities(true, -1, -1);
    }

    public List<Tag> findTagEntities(int maxResults, int firstResult) {
        return findTagEntities(false, maxResults, firstResult);
    }

    private List<Tag> findTagEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tag.class));
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

    public Tag findTag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tag.class, id);
        } finally {
            em.close();
        }
    }

    public int getTagCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tag> rt = cq.from(Tag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
