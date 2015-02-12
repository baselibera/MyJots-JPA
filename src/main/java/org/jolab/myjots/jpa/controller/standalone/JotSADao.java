/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller.standalone;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.jolab.myjots.jpa.model.Jot;
import org.jolab.myjots.jpa.model.Jot_;

/**
 * This is a Controller class for the 'Jot' Entity of a Stand Alone 
 * Appplication library. 
 * 
 * @author jolab
 */
public class JotSADao extends AbstractSADaoFacade<Jot>{

    private EntityManager em;

    public JotSADao(EntityManager em) {
        super(Jot.class);
        this.em = em;
    }

    public List<Jot> findJotsLike(String searchString){
        
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Jot> query = cb.createQuery(entityClass); // entityClass -> Jot.class
    	// You are now ready to begin with criterias definition..
    	// First of all we find all from 'entityClass' 
    	Root<Jot> jotRoot = query.from(entityClass);
    	// Then we can define the results criteria with fluent programming
    	//query.select(jot);
    	query.where(cb.like(jotRoot.get(Jot_.body), "%"+searchString+"%"));
    	
    	// Now we can obtain the executable query with these settings by the query (CriteriaQuery) object
    	TypedQuery<Jot> q = em.createQuery(query);
    	List<Jot> jotList = q.getResultList();
    	System.out.println(jotList);

    	
    	
    	
    	return jotList;
    	 
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
}
