/*
 * EXAMPLE OF INTEGRATION TEST FOR TeamDAOImpl
 */
package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.fofo.entity.Team;
import org.jmock.Expectations;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 *
 * @author josepma
 */
public class TeamDAOImplIntegTest {
    EntityManager em = null;
    
    TeamDAOImpl tdao = null;
    
    public TeamDAOImplIntegTest() {
        
        
    }

 
    @Before
    public void setup() throws Exception{
      
        tdao = new TeamDAOImpl();
        em = getEntityManagerFact(); 
        tdao.setEM(em);
    }
    
    @After
    public void tearDown() throws Exception{
        EntityManager em = getEntityManagerFact();
          em.getTransaction().begin();
        
          Query query=em.createQuery("DELETE FROM Team st");
          int deleteRecords=query.executeUpdate();
         em.getTransaction().commit();
         em.close();
         System.out.println("All records have been deleted.");
         

    }
    
    /**
     * Test of addTeam method, of class TeamDAO.
     */
    @Test 
    public void testAddTeam() throws Exception{
  
         final Team team = new Team("team2");   

                  
         tdao.addTeam(team);
         
         Team teamDB = getTeamFromDB("team2");
         
         
         assertEquals("Should have found the inserted team",
                      team,teamDB);
       
    
    }
    
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
    
    private EntityManager getEntityManagerFact() throws Exception{

     try{

         EntityManagerFactory emf = 
           Persistence.createEntityManagerFactory("fofo");
         return emf.createEntityManager();  

     }
     catch(Exception e){
         System.out.println("ERROR CREATING ENTITY MANAGER FACTORY");
	 throw e;
     }

    }


   private Team getTeamFromDB(String name) throws Exception{

         em = getEntityManagerFact();
         em.getTransaction().begin();
         Team teamDB = em.find(Team.class, name);
         em.getTransaction().commit();
         em.close();
 
         return teamDB; 
         
   } 
    
}
