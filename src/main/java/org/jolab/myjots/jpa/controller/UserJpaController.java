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
import org.jolab.myjots.jpa.model.Role;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jolab.myjots.jpa.controller.exceptions.IllegalOrphanException;
import org.jolab.myjots.jpa.controller.exceptions.NonexistentEntityException;
import org.jolab.myjots.jpa.model.JotUser;
import org.jolab.myjots.jpa.model.User;

/**
 *
 * @author jolab
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getRoleList() == null) {
            user.setRoleList(new ArrayList<Role>());
        }
        if (user.getJotUserList() == null) {
            user.setJotUserList(new ArrayList<JotUser>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Role> attachedRoleList = new ArrayList<Role>();
            for (Role roleListRoleToAttach : user.getRoleList()) {
                roleListRoleToAttach = em.getReference(roleListRoleToAttach.getClass(), roleListRoleToAttach.getIdRole());
                attachedRoleList.add(roleListRoleToAttach);
            }
            user.setRoleList(attachedRoleList);
            List<JotUser> attachedJotUserList = new ArrayList<JotUser>();
            for (JotUser jotUserListJotUserToAttach : user.getJotUserList()) {
                jotUserListJotUserToAttach = em.getReference(jotUserListJotUserToAttach.getClass(), jotUserListJotUserToAttach.getJotUserPK());
                attachedJotUserList.add(jotUserListJotUserToAttach);
            }
            user.setJotUserList(attachedJotUserList);
            em.persist(user);
            for (Role roleListRole : user.getRoleList()) {
                roleListRole.getUserList().add(user);
                roleListRole = em.merge(roleListRole);
            }
            for (JotUser jotUserListJotUser : user.getJotUserList()) {
                User oldUserOfJotUserListJotUser = jotUserListJotUser.getUser();
                jotUserListJotUser.setUser(user);
                jotUserListJotUser = em.merge(jotUserListJotUser);
                if (oldUserOfJotUserListJotUser != null) {
                    oldUserOfJotUserListJotUser.getJotUserList().remove(jotUserListJotUser);
                    oldUserOfJotUserListJotUser = em.merge(oldUserOfJotUserListJotUser);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getIdAppUser());
            List<Role> roleListOld = persistentUser.getRoleList();
            List<Role> roleListNew = user.getRoleList();
            List<JotUser> jotUserListOld = persistentUser.getJotUserList();
            List<JotUser> jotUserListNew = user.getJotUserList();
            List<String> illegalOrphanMessages = null;
            for (JotUser jotUserListOldJotUser : jotUserListOld) {
                if (!jotUserListNew.contains(jotUserListOldJotUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JotUser " + jotUserListOldJotUser + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Role> attachedRoleListNew = new ArrayList<Role>();
            for (Role roleListNewRoleToAttach : roleListNew) {
                roleListNewRoleToAttach = em.getReference(roleListNewRoleToAttach.getClass(), roleListNewRoleToAttach.getIdRole());
                attachedRoleListNew.add(roleListNewRoleToAttach);
            }
            roleListNew = attachedRoleListNew;
            user.setRoleList(roleListNew);
            List<JotUser> attachedJotUserListNew = new ArrayList<JotUser>();
            for (JotUser jotUserListNewJotUserToAttach : jotUserListNew) {
                jotUserListNewJotUserToAttach = em.getReference(jotUserListNewJotUserToAttach.getClass(), jotUserListNewJotUserToAttach.getJotUserPK());
                attachedJotUserListNew.add(jotUserListNewJotUserToAttach);
            }
            jotUserListNew = attachedJotUserListNew;
            user.setJotUserList(jotUserListNew);
            user = em.merge(user);
            for (Role roleListOldRole : roleListOld) {
                if (!roleListNew.contains(roleListOldRole)) {
                    roleListOldRole.getUserList().remove(user);
                    roleListOldRole = em.merge(roleListOldRole);
                }
            }
            for (Role roleListNewRole : roleListNew) {
                if (!roleListOld.contains(roleListNewRole)) {
                    roleListNewRole.getUserList().add(user);
                    roleListNewRole = em.merge(roleListNewRole);
                }
            }
            for (JotUser jotUserListNewJotUser : jotUserListNew) {
                if (!jotUserListOld.contains(jotUserListNewJotUser)) {
                    User oldUserOfJotUserListNewJotUser = jotUserListNewJotUser.getUser();
                    jotUserListNewJotUser.setUser(user);
                    jotUserListNewJotUser = em.merge(jotUserListNewJotUser);
                    if (oldUserOfJotUserListNewJotUser != null && !oldUserOfJotUserListNewJotUser.equals(user)) {
                        oldUserOfJotUserListNewJotUser.getJotUserList().remove(jotUserListNewJotUser);
                        oldUserOfJotUserListNewJotUser = em.merge(oldUserOfJotUserListNewJotUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getIdAppUser();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getIdAppUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<JotUser> jotUserListOrphanCheck = user.getJotUserList();
            for (JotUser jotUserListOrphanCheckJotUser : jotUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the JotUser " + jotUserListOrphanCheckJotUser + " in its jotUserList field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Role> roleList = user.getRoleList();
            for (Role roleListRole : roleList) {
                roleListRole.getUserList().remove(user);
                roleListRole = em.merge(roleListRole);
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

    public User findUser(Integer id) {
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
