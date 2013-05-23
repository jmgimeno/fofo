/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.fofo.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author avk1
 */
public class ClubDAOImplIntegTest {
    private Club club;
    private Team team;
    private ClubDAOImpl clubDao;
    //private EntityManager em;   
    
    public ClubDAOImplIntegTest() {
    }
    
    @Before
    public void setUp() throws Exception {        
        EntityManager em = getEntityManagerFact(); 
        clubDao = new ClubDAOImpl();
        clubDao.setEM(em);  //Ha d'anar aqui o al final del setUp?
        
        /*Setting club & team*/
        club = new Club("testClub1");
        club.setEmail("test1@mail.com");
        
        team = new Team("testTeam1"); 
        team.setClub(club);    
        List<Competition> comps = new ArrayList<Competition>();
//        comps.add(new CompetitionLeague());
        team.setCompetitions(comps);
        try{
            em.getTransaction().begin();
            em.persist(team);
            em.getTransaction().commit();  //PETA AQUI
        }catch(PersistenceException e){
            throw e;
        }finally{
            if(em.isOpen()) em.close();
        }        
        
        List<Team> teams = new ArrayList<Team>();        
        teams.add(team);
        club.setTeams(teams);
        
         
        
    }
    
   
//    @Test
    public void testAddClub() throws Exception {
                
        clubDao.addClub(club);        
    }


//    @Test
    public void testGetClubs() throws PersistException {
        clubDao.addClub(club); 
        
        List<Club> expected = new ArrayList<Club>();
        expected.add(club);
        
        assertEquals("There should be only testClub1",
                expected, clubDao.getClubs());
    }

//    @Test
    public void testFindClubByName() throws PersistException {
        clubDao.addClub(club); 
        assertEquals(club, clubDao.findClubByName("testClub1"));
    }

//    @Test
    public void testFindClubByTeam() throws PersistException {
        clubDao.addClub(club);         
        assertEquals(club, clubDao.findClubByTeam("testTeam1"));
    }
    
    @After
    public void tearDown() throws Exception{
        
        EntityManager em = clubDao.getEM();
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
         
    }
    
    
    
    /* PRIVATE OPS */
    
    private EntityManager getEntityManagerFact() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }


}