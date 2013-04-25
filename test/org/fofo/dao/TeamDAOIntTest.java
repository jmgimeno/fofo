/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.fofo.entity.Team;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 *
 * @author josepma
 */
public class TeamDAOIntTest {
    EntityManager em = null;
    
    TeamDAO tdao = null;
    
    public TeamDAOIntTest() {
        
        tdao = new TeamDAO();
        
    }

 
    @Before
    public void setup() throws Exception{
         em = getEntityManagerFact();
        
          em.getTransaction().begin();
          em.persist(new Team("team1"));
          em.getTransaction().commit();
 
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
    public void testTeam() {
       em.getTransaction().begin(); 
       Team team = (Team) em.find(Team.class, "team1");    
       em.getTransaction().commit();
       
       assertEquals("Should have found the inserted team",
                    new Team("team1"),team);
       
        System.out.println("Team="+team);
    }

    
    
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

    
    
}
