/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller.standalone;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * This class represnt an abstract DAO pattern Facede and define the mininal 
 * common behavior of DAO for the database access.
 * 
 * @author jolab
 */
public abstract class AbstractSADaoFacade<T> {
    protected final Class<T> entityClass;

    public AbstractSADaoFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    
    public void create(T entity, EntityTransaction transaction) throws Exception{
        if(transaction.isActive()){
            EntityManager em = getEntityManager();
            em.persist(entity);
            em.flush();
        }else{
            throw new Exception("Call this method if you want to manage transaction begin/commit/rollback by yourself. Otherwise call create(entity) method");
        }
    }
    
    public void create(T entity) throws Exception{
        EntityTransaction transaction = getEntityManager().getTransaction();
        transaction.begin();
        try{
            create(entity, transaction);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            throw e;
        }
    }

    public T edit(T entity) {
        // return getEntityManager().merge(entity);
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(entity);
            em.flush();
            em.refresh(entity);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            System.out.println("EDIT ERROR: " + e.getMessage());
            entity = null;
        }finally{
            return entity;
        }
        
    }

    public void remove(T entity) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(getEntityManager().merge(entity));
            em.flush();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
            System.out.println("REMOVE ERROR: " + e.getMessage());
        }finally{
            
        }
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public T findByPk(Object pk) {
            T result = getEntityManager().find(entityClass , pk);
            return result;
    }
    
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }    
    
    
    private List<T> findWithPagination(boolean all, int maxResults, int firstResult) {
        
        try {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            Query q = getEntityManager().createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            
        }
    }
}
