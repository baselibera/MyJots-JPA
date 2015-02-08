/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.jolab.myjots.jpa.model.Jot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jolab
 */
public class JotJpaControllerTest {
    
    private static final String PERSISTENCE_UNIT_NAME = "MyJots-JPA-0.1-SNAPSHOT-PU";
    private static EntityManagerFactory factory;

    
    public JotJpaControllerTest() {

    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Prepare Entity Manager Factory");
        JotJpaControllerTest.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if(factory==null)
            fail("Entity Manager Factory not properly created.");   
        
        Map<String, Object> properties = JotJpaControllerTest.factory.getProperties();
        Set<String> propertiesNames = properties.keySet();
        for (String propertiesName : propertiesNames) {
            System.out.println(propertiesName + " = " + properties.get(propertiesName));
        }
        
        
        System.out.println("Done... " );
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        JotJpaControllerTest.factory.close();
        
    }
    
    @Before
    public void setUp() {
        System.out.println("setUp");
    }
    
    @After
    public void tearDown() {
        System.out.println("tearDown");
    }


    /**
     * Test of create method, of class JotJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Jot jot = createJot();
        
        assertEquals(true, jot.getIdJot()!=null);
        
    }

    private Jot createJot() {
        Jot jot = new Jot();
        jot.setTitle("Titolo Uno");
        jot.setBody("Questo è il testo del Test");
        jot.setMimeType("text/plain");
        jot.setStatus(1);
        jot.setCreateTime(new Date());
        JotJpaController instance = new JotJpaController(factory);
        try{
            instance.create(jot);
        }catch(Exception e){
            e.printStackTrace();
        }
        return jot;
    }



    /**
     * Test of getJotCount method, of class JotJpaController.
     */
    @Test
    public void testGetJotCount() {
        System.out.println("getJotCount");
        JotJpaController instance = new JotJpaController(factory);
        int expResult = 0;
        int result = instance.getJotCount();
        assertEquals(expResult, result);
        
    }
    
}
