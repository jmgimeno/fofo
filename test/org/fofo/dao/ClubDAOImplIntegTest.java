/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.AlreadyExistingClubOrTeamsException;
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
 * @author Anatoli, Mohamed
 */
public class ClubDAOImplIntegTest {
    private Club club;
    private Team team;
    private ClubDAOImpl clubDao;    
    private EntityManager em = null;

        
    public ClubDAOImplIntegTest() {
    }
    
    @Before
    public void setUp() throws Exception {        
        em = getEntityManager(); 
        clubDao = new ClubDAOImpl();
        clubDao.setEM(em);   
                
        /*Setting club & team*/
        club = new Club("testClub1");
        club.setEmail("test1@mail.com");
   
        team = new Team("testTeam1"); 
        team.setClub(club);    
//        List<Competition> comps = new ArrayList<Competition>();
//        team.setCompetitions(comps);
        
        List<Team> teams = new ArrayList<Team>();        
        teams.add(team);
        club.setTeams(teams);
        
         
        
    }
    
   
    @Test
    public void testAddClub() throws Exception {
                
        clubDao.addClub(club);        
        
        Club clubDB = getClubFromDB("testClub1");
         
        System.out.println(clubDB);
        
        assertEquals("Should have found the inserted club",
                      club,clubDB);
        
        
        
    }  
    
    @Test (expected=AlreadyExistingClubOrTeamsException.class)
    public void alreadyExistClubInDB() throws Exception{
        clubDao.addClub(club);
        clubDao.addClub(club);
        
    }
    
    @Test (expected=AlreadyExistingClubOrTeamsException.class)
    public void alreadyExistTeamsOfClubInDB() throws Exception{
        Club c = new Club("testClub2");
        c.setTeams(club.getTeams());
        
        clubDao.addClub(club);
        clubDao.addClub(c);
    }
     
    
    @Test  
    public void testGetClubs() throws Exception {
        clubDao.addClub(club); 
        
        List<Club> expected = new ArrayList<Club>();
        expected.add(club);
        
        assertEquals("There should be only testClub1",
                expected, clubDao.getClubs());
    }

    @Test
    public void testFindClubByName() throws Exception {
        clubDao.addClub(club); 
        assertEquals(club, clubDao.findClubByName("testClub1"));
    }

    @Test
    public void testFindClubByTeam() throws Exception {
        clubDao.addClub(club);         
        assertEquals(club, clubDao.findClubByTeam("testTeam1"));
    }

    @After
    public void tearDown() throws Exception{
        
        em = clubDao.getEM();
        if (em.isOpen()) em.close();
        
        em = getEntityManager();
        em.getTransaction().begin();
        
        Query query=em.createQuery("DELETE FROM Team");
        Query query2=em.createQuery("DELETE FROM Club");
        int deleteRecords=query.executeUpdate();
        deleteRecords=query2.executeUpdate();
        
        em.getTransaction().commit();
        em.close();
        System.out.println("All records have been deleted.");
         
    }
    
    
    
    /* PRIVATE OPS */
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }

    private Club getClubFromDB(String name) throws Exception{        

        em = getEntityManager();
        em.getTransaction().begin();
        Club clubDB = (Club) em.find(Club.class, name);
        em.getTransaction().commit();
        em.close();

        return clubDB; 
         
   }
    

}