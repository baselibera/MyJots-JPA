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
import org.jolab.myjots.jpa.model.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jolab.myjots.jpa.controller.exceptions.IllegalOrphanException;
import org.jolab.myjots.jpa.controller.exceptions.NonexistentEntityException;
import org.jolab.myjots.jpa.model.Jot;
import org.jolab.myjots.jpa.model.Link;
import org.jolab.myjots.jpa.model.JotHistory;
import org.jolab.myjots.jpa.model.JotUser;
import org.jolab.myjots.jpa.model.JotReferJot;

/**
 *
 * @author jolab
 */
public class JotJpaController implements Serializable {

    public JotJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jot jot) {
        if (jot.getTagList() == null) {
            jot.setTagList(new ArrayList<Tag>());
        }
        if (jot.getLinkList() == null) {
            jot.setLinkList(new ArrayList<Link>());
        }
        if (jot.getJotHistoryList() == null) {
            jot.setJotHistoryList(new ArrayList<JotHistory>());
        }
        if (jot.getJotUserList() == null) {
            jot.setJotUserList(new ArrayList<JotUser>());
        }
        if (jot.getJotReferJotList() == null) {
            jot.setJotReferJotList(new ArrayList<JotReferJot>());
        }
        if (jot.getJotReferJotList1() == null) {
            jot.setJotReferJotList1(new ArrayList<JotReferJot>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tag> attachedTagList = new ArrayList<Tag>();
            for (Tag tagListTagToAttach : jot.getTagList()) {
                tagListTagToAttach = em.getReference(tagListTagToAttach.getClass(), tagListTagToAttach.getIdTag());
                attachedTagList.add(tagListTagToAttach);
            }
            jot.setTagList(attachedTagList);
            List<Link> attachedLinkList = new ArrayList<Link>();
            for (Link linkListLinkToAttach : jot.getLinkList()) {
                linkListLinkToAttach = em.getReference(linkListLinkToAttach.getClass(), linkListLinkToAttach.getIdLink());
                attachedLinkList.add(linkListLinkToAttach);
            }
            jot.setLinkList(attachedLinkList);
            List<JotHistory> attachedJotHistoryList = new ArrayList<JotHistory>();
            for (JotHistory jotHistoryListJotHistoryToAttach : jot.getJotHistoryList()) {
                jotHistoryListJotHistoryToAttach = em.getReference(jotHistoryListJotHistoryToAttach.getClass(), jotHistoryListJotHistoryToAttach.getJotHistoryPK());
                attachedJotHistoryList.add(jotHistoryListJotHistoryToAttach);
            }
            jot.setJotHistoryList(attachedJotHistoryList);
            List<JotUser> attachedJotUserList = new ArrayList<JotUser>();
            for (JotUser jotUserListJotUserToAttach : jot.getJotUserList()) {
                jotUserListJotUserToAttach = em.getReference(jotUserListJotUserToAttach.getClass(), jotUserListJotUserToAttach.getJotUserPK());
                attachedJotUserList.add(jotUserListJotUserToAttach);
            }
            jot.setJotUserList(attachedJotUserList);
            List<JotReferJot> attachedJotReferJotList = new ArrayList<JotReferJot>();
            for (JotReferJot jotReferJotListJotReferJotToAttach : jot.getJotReferJotList()) {
                jotReferJotListJotReferJotToAttach = em.getReference(jotReferJotListJotReferJotToAttach.getClass(), jotReferJotListJotReferJotToAttach.getJotReferJotPK());
                attachedJotReferJotList.add(jotReferJotListJotReferJotToAttach);
            }
            jot.setJotReferJotList(attachedJotReferJotList);
            List<JotReferJot> attachedJotReferJotList1 = new ArrayList<JotReferJot>();
            for (JotReferJot jotReferJotList1JotReferJotToAttach : jot.getJotReferJotList1()) {
                jotReferJotList1JotReferJotToAttach = em.getReference(jotReferJotList1JotReferJotToAttach.getClass(), jotReferJotList1JotReferJotToAttach.getJotReferJotPK());
                attachedJotReferJotList1.add(jotReferJotList1JotReferJotToAttach);
            }
            jot.setJotReferJotList1(attachedJotReferJotList1);
            em.persist(jot);
            for (Tag tagListTag : jot.getTagList()) {
                tagListTag.getJotList().add(jot);
                tagListTag = em.merge(tagListTag);
            }
            for (Link linkListLink : jot.getLinkList()) {
                Jot oldIdJotOfLinkListLink = linkListLink.getIdJot();
                linkListLink.setIdJot(jot);
                linkListLink = em.merge(linkListLink);
                if (oldIdJotOfLinkListLink != null) {
                    oldIdJotOfLinkListLink.getLinkList().remove(linkListLink);
                    oldIdJotOfLinkListLink = em.merge(oldIdJotOfLinkListLink);
                }
            }
            for (JotHistory jotHistoryListJotHistory : jot.getJotHistoryList()) {
                Jot oldJotOfJotHistoryListJotHistory = jotHistoryListJotHistory.getJot();
                jotHistoryListJotHistory.setJot(jot);
                jotHistoryListJotHistory = em.merge(jotHistoryListJotHistory);
                if (oldJotOfJotHistoryListJotHistory != null) {
                    oldJotOfJotHistoryListJotHistory.getJotHistoryList().remove(jotHistoryListJotHistory);
                    oldJotOfJotHistoryListJotHistory = em.merge(oldJotOfJotHistoryListJotHistory);
                }
            }
            for (JotUser jotUserListJotUser : jot.getJotUserList()) {
                Jot oldJotOfJotUserListJotUser = jotUserListJotUser.getJot();
                jotUserListJotUser.setJot(jot);
                jotUserListJotUser = em.merge(jotUserListJotUser);
                if (oldJotOfJotUserListJotUser != null) {
                    oldJotOfJotUserListJotUser.getJotUserList().remove(jotUserListJotUser);
                    oldJotOfJotUserListJotUser = em.merge(oldJotOfJotUserListJotUser);
                }
            }
            for (JotReferJot jotReferJotListJotReferJot : jot.getJotReferJotList()) {
                Jot oldJotOfJotReferJotListJotReferJot = jotReferJotListJotReferJot.getJot();
                jotReferJotListJotReferJot.setJot(jot);
                jotReferJotListJotReferJot = em.merge(jotReferJotListJotReferJot);
                if (oldJotOfJotReferJotListJotReferJot != null) {
                    oldJotOfJotReferJotListJotReferJot.getJotReferJotList().remove(jotReferJotListJotReferJot);
                    oldJotOfJotReferJotListJotReferJot = em.merge(oldJotOfJotReferJotListJotReferJot);
                }
            }
            for (JotReferJot jotReferJotList1JotReferJot : jot.getJotReferJotList1()) {
                Jot oldJot1OfJotReferJotList1JotReferJot = jotReferJotList1JotReferJot.getJot1();
                jotReferJotList1JotReferJot.setJot1(jot);
                jotReferJotList1JotReferJot = em.merge(jotReferJotList1JotReferJot);
                if (oldJot1OfJotReferJotList1JotReferJot != null) {
                    oldJot1OfJotReferJotList1JotReferJot.getJotReferJotList1().remove(jotReferJotList1JotReferJot);
                    oldJot1OfJotReferJotList1JotReferJot = em.merge(oldJot1OfJotReferJotList1JotReferJot);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jot jot) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jot persistentJot = em.find(Jot.class, jot.getIdJot());
            List<Tag> tagListOld = persistentJot.getTagList();
            List<Tag> tagListNew = jot.getTagList();
            List<Link> linkListOld = persistentJot.getLinkList();
            List<Link> linkListNew = jot.getLinkList();
            List<JotHistory> jotHistoryListOld = persistentJot.getJotHistoryList();
            List<JotHistory> jotHistoryListNew = jot.getJotHistoryList();
            List<JotUser> jotUserListOld = persistentJot.getJotUserList();
            List<JotUser> jotUserListNew = jot.getJotUserList();
            List<JotReferJot> jotReferJotListOld = persistentJot.getJotReferJotList();
            List<JotReferJot> jotReferJotListNew = jot.getJotReferJotList();
            List<JotReferJot> jotReferJotList1Old = persistentJot.getJotReferJotList1();
            List<JotReferJot> jotReferJotList1New = jot.getJotReferJotList1();
            List<String> illegalOrphanMessages = null;
            for (Link linkListOldLink : linkListOld) {
                if (!linkListNew.contains(linkListOldLink)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Link " + linkListOldLink + " since its idJot field is not nullable.");
                }
            }
            for (JotHistory jotHistoryListOldJotHistory : jotHistoryListOld) {
                if (!jotHistoryListNew.contains(jotHistoryListOldJotHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JotHistory " + jotHistoryListOldJotHistory + " since its jot field is not nullable.");
                }
            }
            for (JotUser jotUserListOldJotUser : jotUserListOld) {
                if (!jotUserListNew.contains(jotUserListOldJotUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JotUser " + jotUserListOldJotUser + " since its jot field is not nullable.");
                }
            }
            for (JotReferJot jotReferJotListOldJotReferJot : jotReferJotListOld) {
                if (!jotReferJotListNew.contains(jotReferJotListOldJotReferJot)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JotReferJot " + jotReferJotListOldJotReferJot + " since its jot field is not nullable.");
                }
            }
            for (JotReferJot jotReferJotList1OldJotReferJot : jotReferJotList1Old) {
                if (!jotReferJotList1New.contains(jotReferJotList1OldJotReferJot)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JotReferJot " + jotReferJotList1OldJotReferJot + " since its jot1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tag> attachedTagListNew = new ArrayList<Tag>();
            for (Tag tagListNewTagToAttach : tagListNew) {
                tagListNewTagToAttach = em.getReference(tagListNewTagToAttach.getClass(), tagListNewTagToAttach.getIdTag());
                attachedTagListNew.add(tagListNewTagToAttach);
            }
            tagListNew = attachedTagListNew;
            jot.setTagList(tagListNew);
            List<Link> attachedLinkListNew = new ArrayList<Link>();
            for (Link linkListNewLinkToAttach : linkListNew) {
                linkListNewLinkToAttach = em.getReference(linkListNewLinkToAttach.getClass(), linkListNewLinkToAttach.getIdLink());
                attachedLinkListNew.add(linkListNewLinkToAttach);
            }
            linkListNew = attachedLinkListNew;
            jot.setLinkList(linkListNew);
            List<JotHistory> attachedJotHistoryListNew = new ArrayList<JotHistory>();
            for (JotHistory jotHistoryListNewJotHistoryToAttach : jotHistoryListNew) {
                jotHistoryListNewJotHistoryToAttach = em.getReference(jotHistoryListNewJotHistoryToAttach.getClass(), jotHistoryListNewJotHistoryToAttach.getJotHistoryPK());
                attachedJotHistoryListNew.add(jotHistoryListNewJotHistoryToAttach);
            }
            jotHistoryListNew = attachedJotHistoryListNew;
            jot.setJotHistoryList(jotHistoryListNew);
            List<JotUser> attachedJotUserListNew = new ArrayList<JotUser>();
            for (JotUser jotUserListNewJotUserToAttach : jotUserListNew) {
                jotUserListNewJotUserToAttach = em.getReference(jotUserListNewJotUserToAttach.getClass(), jotUserListNewJotUserToAttach.getJotUserPK());
                attachedJotUserListNew.add(jotUserListNewJotUserToAttach);
            }
            jotUserListNew = attachedJotUserListNew;
            jot.setJotUserList(jotUserListNew);
            List<JotReferJot> attachedJotReferJotListNew = new ArrayList<JotReferJot>();
            for (JotReferJot jotReferJotListNewJotReferJotToAttach : jotReferJotListNew) {
                jotReferJotListNewJotReferJotToAttach = em.getReference(jotReferJotListNewJotReferJotToAttach.getClass(), jotReferJotListNewJotReferJotToAttach.getJotReferJotPK());
                attachedJotReferJotListNew.add(jotReferJotListNewJotReferJotToAttach);
            }
            jotReferJotListNew = attachedJotReferJotListNew;
            jot.setJotReferJotList(jotReferJotListNew);
            List<JotReferJot> attachedJotReferJotList1New = new ArrayList<JotReferJot>();
            for (JotReferJot jotReferJotList1NewJotReferJotToAttach : jotReferJotList1New) {
                jotReferJotList1NewJotReferJotToAttach = em.getReference(jotReferJotList1NewJotReferJotToAttach.getClass(), jotReferJotList1NewJotReferJotToAttach.getJotReferJotPK());
                attachedJotReferJotList1New.add(jotReferJotList1NewJotReferJotToAttach);
            }
            jotReferJotList1New = attachedJotReferJotList1New;
            jot.setJotReferJotList1(jotReferJotList1New);
            jot = em.merge(jot);
            for (Tag tagListOldTag : tagListOld) {
                if (!tagListNew.contains(tagListOldTag)) {
                    tagListOldTag.getJotList().remove(jot);
                    tagListOldTag = em.merge(tagListOldTag);
                }
            }
            for (Tag tagListNewTag : tagListNew) {
                if (!tagListOld.contains(tagListNewTag)) {
                    tagListNewTag.getJotList().add(jot);
                    tagListNewTag = em.merge(tagListNewTag);
                }
            }
            for (Link linkListNewLink : linkListNew) {
                if (!linkListOld.contains(linkListNewLink)) {
                    Jot oldIdJotOfLinkListNewLink = linkListNewLink.getIdJot();
                    linkListNewLink.setIdJot(jot);
                    linkListNewLink = em.merge(linkListNewLink);
                    if (oldIdJotOfLinkListNewLink != null && !oldIdJotOfLinkListNewLink.equals(jot)) {
                        oldIdJotOfLinkListNewLink.getLinkList().remove(linkListNewLink);
                        oldIdJotOfLinkListNewLink = em.merge(oldIdJotOfLinkListNewLink);
                    }
                }
            }
            for (JotHistory jotHistoryListNewJotHistory : jotHistoryListNew) {
                if (!jotHistoryListOld.contains(jotHistoryListNewJotHistory)) {
                    Jot oldJotOfJotHistoryListNewJotHistory = jotHistoryListNewJotHistory.getJot();
                    jotHistoryListNewJotHistory.setJot(jot);
                    jotHistoryListNewJotHistory = em.merge(jotHistoryListNewJotHistory);
                    if (oldJotOfJotHistoryListNewJotHistory != null && !oldJotOfJotHistoryListNewJotHistory.equals(jot)) {
                        oldJotOfJotHistoryListNewJotHistory.getJotHistoryList().remove(jotHistoryListNewJotHistory);
                        oldJotOfJotHistoryListNewJotHistory = em.merge(oldJotOfJotHistoryListNewJotHistory);
                    }
                }
            }
            for (JotUser jotUserListNewJotUser : jotUserListNew) {
                if (!jotUserListOld.contains(jotUserListNewJotUser)) {
                    Jot oldJotOfJotUserListNewJotUser = jotUserListNewJotUser.getJot();
                    jotUserListNewJotUser.setJot(jot);
                    jotUserListNewJotUser = em.merge(jotUserListNewJotUser);
                    if (oldJotOfJotUserListNewJotUser != null && !oldJotOfJotUserListNewJotUser.equals(jot)) {
                        oldJotOfJotUserListNewJotUser.getJotUserList().remove(jotUserListNewJotUser);
                        oldJotOfJotUserListNewJotUser = em.merge(oldJotOfJotUserListNewJotUser);
                    }
                }
            }
            for (JotReferJot jotReferJotListNewJotReferJot : jotReferJotListNew) {
                if (!jotReferJotListOld.contains(jotReferJotListNewJotReferJot)) {
                    Jot oldJotOfJotReferJotListNewJotReferJot = jotReferJotListNewJotReferJot.getJot();
                    jotReferJotListNewJotReferJot.setJot(jot);
                    jotReferJotListNewJotReferJot = em.merge(jotReferJotListNewJotReferJot);
                    if (oldJotOfJotReferJotListNewJotReferJot != null && !oldJotOfJotReferJotListNewJotReferJot.equals(jot)) {
                        oldJotOfJotReferJotListNewJotReferJot.getJotReferJotList().remove(jotReferJotListNewJotReferJot);
                        oldJotOfJotReferJotListNewJotReferJot = em.merge(oldJotOfJotReferJotListNewJotReferJot);
                    }
                }
            }
            for (JotReferJot jotReferJotList1NewJotReferJot : jotReferJotList1New) {
                if (!jotReferJotList1Old.contains(jotReferJotList1NewJotReferJot)) {
                    Jot oldJot1OfJotReferJotList1NewJotReferJot = jotReferJotList1NewJotReferJot.getJot1();
                    jotReferJotList1NewJotReferJot.setJot1(jot);
                    jotReferJotList1NewJotReferJot = em.merge(jotReferJotList1NewJotReferJot);
                    if (oldJot1OfJotReferJotList1NewJotReferJot != null && !oldJot1OfJotReferJotList1NewJotReferJot.equals(jot)) {
                        oldJot1OfJotReferJotList1NewJotReferJot.getJotReferJotList1().remove(jotReferJotList1NewJotReferJot);
                        oldJot1OfJotReferJotList1NewJotReferJot = em.merge(oldJot1OfJotReferJotList1NewJotReferJot);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jot.getIdJot();
                if (findJot(id) == null) {
                    throw new NonexistentEntityException("The jot with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jot jot;
            try {
                jot = em.getReference(Jot.class, id);
                jot.getIdJot();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jot with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Link> linkListOrphanCheck = jot.getLinkList();
            for (Link linkListOrphanCheckLink : linkListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jot (" + jot + ") cannot be destroyed since the Link " + linkListOrphanCheckLink + " in its linkList field has a non-nullable idJot field.");
            }
            List<JotHistory> jotHistoryListOrphanCheck = jot.getJotHistoryList();
            for (JotHistory jotHistoryListOrphanCheckJotHistory : jotHistoryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jot (" + jot + ") cannot be destroyed since the JotHistory " + jotHistoryListOrphanCheckJotHistory + " in its jotHistoryList field has a non-nullable jot field.");
            }
            List<JotUser> jotUserListOrphanCheck = jot.getJotUserList();
            for (JotUser jotUserListOrphanCheckJotUser : jotUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jot (" + jot + ") cannot be destroyed since the JotUser " + jotUserListOrphanCheckJotUser + " in its jotUserList field has a non-nullable jot field.");
            }
            List<JotReferJot> jotReferJotListOrphanCheck = jot.getJotReferJotList();
            for (JotReferJot jotReferJotListOrphanCheckJotReferJot : jotReferJotListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jot (" + jot + ") cannot be destroyed since the JotReferJot " + jotReferJotListOrphanCheckJotReferJot + " in its jotReferJotList field has a non-nullable jot field.");
            }
            List<JotReferJot> jotReferJotList1OrphanCheck = jot.getJotReferJotList1();
            for (JotReferJot jotReferJotList1OrphanCheckJotReferJot : jotReferJotList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jot (" + jot + ") cannot be destroyed since the JotReferJot " + jotReferJotList1OrphanCheckJotReferJot + " in its jotReferJotList1 field has a non-nullable jot1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tag> tagList = jot.getTagList();
            for (Tag tagListTag : tagList) {
                tagListTag.getJotList().remove(jot);
                tagListTag = em.merge(tagListTag);
            }
            em.remove(jot);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jot> findJotEntities() {
        return findJotEntities(true, -1, -1);
    }

    public List<Jot> findJotEntities(int maxResults, int firstResult) {
        return findJotEntities(false, maxResults, firstResult);
    }

    private List<Jot> findJotEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jot.class));
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

    public Jot findJot(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jot.class, id);
        } finally {
            em.close();
        }
    }

    public int getJotCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jot> rt = cq.from(Jot.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
