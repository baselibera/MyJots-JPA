/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller.standalone;

import javax.persistence.EntityManager;
import org.jolab.myjots.jpa.model.Jot;

/**
 * This is a Controller class for the 'Jot' Entity of a Stand Alone 
 * Appplication library. 
 * 
 * @author jolab
 */
public class JotSADao extends AbstractSADaoFacade{

    private EntityManager em;
    
    public JotSADao(EntityManager em) {
        super(Jot.class);
        this.em = em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
