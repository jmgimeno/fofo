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
import org.fofo.entity.Club;
import org.jmock.Expectations;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 *
 * @author josepma
 */
public class TeamDAOImplInteg2Test {
    EntityManager em = null;
    
    TeamDAOImpl tdao = null;
    
    Club club;       
    
    public TeamDAOImplInteg2Test() {
        
        
    }

 
    @Before
    public void setup() throws Exception{
      
         club = new Club();
         club.setName("Lleida");
         club.setEmail("lleida@lleida.net");
        
         tdao = new TeamDAOImpl();
          
         em = getEntityManagerFact(); 
       /*
         em.getTransaction().begin();
         em.persist(club);
         em.getTransaction().commit();
         
         tdao.setEM(em);
        */
    }
    
    
    @After
    public void tearDown() throws Exception{
   /*  
        EntityManager em = tdao.getEM();
 
        if (em.isOpen()) em.close();
        
        em = getEntityManagerFact();
        em.getTransaction().begin();
        
        Query query=em.createQuery("DELETE FROM Team st");
        Query query2=em.createQuery("DELETE FROM Club cl");
        int deleteRecords=query.executeUpdate();
        deleteRecords=query2.executeUpdate();
          
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
         
     */
    }
    
    /**
     * Test of addTeam method, of class TeamDAO.
     */
    
    @Test 
    public void testAddTeam() throws Exception{
      /*
         final Team team = new Team("team2");   

         team.setClub(club);
                  
         tdao.addTeam(team);
         
         Team teamDB = getTeamFromDB("team2");
         
         assertEquals("Should have found the inserted team",
                      team,teamDB);
      */
    }
    
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
    
    private EntityManager getEntityManagerFact() throws Exception{
         EntityManagerFactory emf = 
           Persistence.createEntityManagerFactory("fofo");
         
         return emf.createEntityManager();  
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
