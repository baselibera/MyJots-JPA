/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.jolab.myjots.jpa.model.Role;

/**
 *
 * @author jolab
 */
public class StandaloneApplication {

    private static final String PERSISTENCE_UNIT_NAME = "MyJots-JPA-0.1-SNAPSHOT-PU";
    private static EntityManagerFactory factory;

    public static void main(String[] args){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("SELECT r FROM Approle r");
        List<Role> roles = q.getResultList();
        for (Role role : roles) {
            System.out.println(role.getIdRole() + " - " + role.getName());
        }
        System.out.println("Numero di record:" + roles.size());

        em.close();
        factory.close();
    }

    
}
