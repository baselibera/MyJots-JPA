/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jolab.myjots.jpa.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.jolab.myjots.jpa.controller.standalone.JotHistoryDao;
import org.jolab.myjots.jpa.controller.standalone.JotSADao;
import org.jolab.myjots.jpa.model.Jot;
import org.jolab.myjots.jpa.model.JotHistory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jolab
 */
public class JotJpaControllerTest {
    
    private static final String PERSISTENCE_UNIT_NAME = "MyJots-JPA-0.1-SNAPSHOT-PU";
    private static EntityManagerFactory emFactory;
    private static EntityManager em;
    private static final boolean verifyProperties = false;
    
    public JotJpaControllerTest() {

    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Init... Prepare Entity Manager Factory");
        JotJpaControllerTest.emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if(emFactory==null)
            fail("Entity Manager Factory not properly created.");   
        
        JotJpaControllerTest.em = emFactory.createEntityManager();
        if(em==null)
            fail("Entity Manager not properly created.");   
        
        // Optional debug functionality
        if(verifyProperties){
            Map<String, Object> properties = JotJpaControllerTest.emFactory.getProperties();
            Set<String> propertiesNames = properties.keySet();
            for (String propertiesName : propertiesNames) {
                System.out.println(propertiesName + " = " + properties.get(propertiesName));
            }
        }        
        
        System.out.println("Initialization finished... " );
        
        insertJotRecords(em);
        
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        deleteJotRecords(JotJpaControllerTest.em);
        
        try{
            JotJpaControllerTest.em.close();
            JotJpaControllerTest.emFactory.close();
        }catch(Exception e ){
            
        }
        
        
    }
    
    private static void insertJotRecords(EntityManager em) throws Exception{
        executeSQLScriptFromBundle(em, "insertjot.");
    }

    private static void deleteJotRecords(EntityManager em) throws Exception{
        executeSQLScriptFromBundle(em, "deletejot.");
    }
    
    
    private static void executeSQLScriptFromBundle(EntityManager em, String prefix) throws Exception{
        ResourceBundle res = ResourceBundle.getBundle("Bundle");
        Enumeration<String> sqlStatements = res.getKeys();
        em.getTransaction().begin();
        boolean rollback = false;
        while (sqlStatements.hasMoreElements()) {
            String insertKey = sqlStatements.nextElement();
            if(insertKey.startsWith(prefix)){
                String sqlScript = res.getString(insertKey);
                try{
                    Query q = em.createNativeQuery(sqlScript);
                    q.executeUpdate();          
                }catch(Exception e){
                    e.printStackTrace();
                    rollback=true;
                    break;
                }
            }
        }
        if(rollback){
            em.getTransaction().rollback();
            throw new Exception("Error during Tests initialization");
        }else{
            em.getTransaction().commit();
        }
        
    }
   
    
    /*
    @Before and @After are executed for each @Test annotated method
    */
    /*
    @Before
    public void setUp() {
        // System.out.println("setUp");
    }
    
    @After
    public void tearDown() {
        // System.out.println("tearDown");
    }
    */
        
    
    @Test
    public void testSAJotDaoFacade(){
        EntityManager em = emFactory.createEntityManager();
        JotSADao jotDao = new JotSADao(em);
        
        //Create a new Jot Record...
            Jot jot = new Jot();
        jot.setTitle("Note Title");
        jot.setBody("This is the body for this example Jot. Other more significan test could be done but this minimum if suffice, for now.");
        jot.setMimeType("text/plain");
        jot.setStatus(1);
        jot.setCreateTime(new Date());        
        
        jotDao.create(jot);
        assertEquals(true, jot.getIdJot()!=null);
        
        // Retrieve the just inserted record as jpa object
        Integer idJot = jot.getIdJot();
        Jot retrievedJot = (Jot)jotDao.findByPk(idJot);
        
        // Modify mapped object in some property
        StringBuilder newBody = new StringBuilder("##Modified##.");
        String oldBody = retrievedJot.getBody();
        retrievedJot.setBody( newBody.append(retrievedJot.getBody()).toString());
        retrievedJot.setUpdateTime(new Date());
        Jot editedJot = (Jot)jotDao.edit(retrievedJot);
        assertTrue("Record seems to be not modified yet.", editedJot.getBody().contains("##Modified##"));
        
        // Prepare and create history record
        JotHistory jotHisto1 = prepareJotHistory(retrievedJot);
        retrievedJot.setBody("Modified for the second time." + retrievedJot.getBody());
        JotHistory jotHisto2 = prepareJotHistory(retrievedJot);

        JotHistoryDao jhDao = new JotHistoryDao(em);
        jhDao.create(jotHisto1);        
        jhDao.create(jotHisto2);   
        // Assert success the insert of JotHistory objects with direct selection
        List<JotHistory> historicalJots = jhDao.findJotHistoryByJotId(retrievedJot.getIdJot());
        assertNotNull(historicalJots);
        assertTrue("Jot History Item doesn't correspond to associated items", historicalJots.size()==2);

        // NOTE: 'JotHistory' is declarative fetched LAZY
        // Needed to obtain database consistent object 
        // in this case we find history record loaded
        em.refresh(retrievedJot);
        assertNotNull(retrievedJot.getJotHistoryList().size()==2);
        assertTrue("number of History item for the refreshed Jot are wrong.", retrievedJot.getJotHistoryList().size()==2);
        // So revoming a refreshed Jot with 2 jotHistory record attached 
        // we remove also children objects
        jotDao.remove(retrievedJot);
        
        Jot deletedJot = (Jot)jotDao.findByPk(idJot);
        assertNull("Jot not removed!", deletedJot);
        
    }

    private JotHistory prepareJotHistory(Jot parentJotObject){
        JotHistory jotHistory = new JotHistory();
        jotHistory.setIdJot(parentJotObject);
        jotHistory.setObject(parentJotObject.getBody().getBytes());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        jotHistory.setSavedate(df.format(new Date()));
        
        return jotHistory;
    }
    
    /**
     * Test of getJotCount method, of class JotJpaController.
     */
    @Test
    public void testGetJotCount() {
        
        JotSADao jotDao = new JotSADao(em);

        int result = jotDao.count();
        assertTrue(result>0);
        
    }
    
}
