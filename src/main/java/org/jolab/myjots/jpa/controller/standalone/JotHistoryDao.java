/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller.standalone;

import java.util.List;
import javax.persistence.EntityManager;
import org.jolab.myjots.jpa.model.JotHistory;

/**
 *
 * @author jolab
 */
public class JotHistoryDao extends AbstractSADaoFacade{

    private EntityManager em;
    
    public JotHistoryDao(EntityManager em) {
        super(JotHistoryDao.class);
        this.em = em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<JotHistory> findJotHistoryByJotId(Integer jotId){
        
        List<JotHistory> results = em.createNamedQuery("JotHistory.findByIdJot")
            .setParameter("idJot", jotId)
            .getResultList();
        return results;
        
        
    }
}
