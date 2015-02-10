/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller.standalone;

import java.util.List;
import javax.persistence.EntityManager;
import org.jolab.myjots.jpa.model.Jot;

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
        
        // TODO filter title and body with input string using JPA criteria
        
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();        
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
}
