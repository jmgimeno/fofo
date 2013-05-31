/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.entity.Referee;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 *
 * @author Anatoli, Mohamed
 */
public class RefereeDAOImplIntegTest {
    
    private RefereeDAOImpl refDao;
    private EntityManager em;
    private Referee referee;
    
    public RefereeDAOImplIntegTest() {
    }
    
    @Before
    public void setUp() throws Exception{
        em = getEntityManager();
        refDao = new RefereeDAOImpl();
        refDao.setEM(em);
        
        referee = new Referee("refereeNif","refereeName");
        
    }


    @Test
    public void testAddReferee() throws Exception {
        
        refDao.addReferee(referee);        
        
        Referee refereeDB = getRefereeFromDB(referee.getNif());

        assertEquals("Should have found the inserted referee",
                      referee,refereeDB);
    }

    @Test (expected = AlreadyExistingRefereeException.class)
    public void alreadyExistRefereeInDB() throws Exception {
        refDao.addReferee(referee);
        refDao.addReferee(referee);
    }    

    @Test
    public void testFindRefereeByNif() throws Exception {
        refDao.addReferee(referee);
        assertEquals(referee,refDao.findRefereeByNif(referee.getNif()));
    }

    @Test
    public void testGetAllReferees() throws Exception {
        refDao.addReferee(referee);
        
        List<Referee> expected = new ArrayList<Referee>();
        expected.add(referee);
        
        assertEquals(expected,refDao.getAllReferees());
    }
    
    @After
    public void tearDown() throws Exception{
        
        em = refDao.getEM();
        if (em.isOpen()) em.close();
        
        em = getEntityManager();
        em.getTransaction().begin();
        
        Query query=em.createQuery("DELETE FROM Referee");        
        int deleteRecords=query.executeUpdate();        

        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
         
    }
    
    
    /* PRIVATE OPS */
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }
    
    private Referee getRefereeFromDB(String nif) throws Exception{        

        em = getEntityManager();
        em.getTransaction().begin();
        Referee refDB = (Referee) em.find(Referee.class, nif);
        em.getTransaction().commit();
        em.close();

        return refDB; 
         
   }
}