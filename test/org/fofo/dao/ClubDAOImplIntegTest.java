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
import org.fofo.entity.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author avk1
 */
public class ClubDAOImplIntegTest {
    private Club club1, club2;
    private ClubDAOImpl clubDao;
    
    
    public ClubDAOImplIntegTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        setUpClubs();        
        
        EntityManager em = getEntityManager(); //DON'T WORK
        
        clubDao = new ClubDAOImpl();
        clubDao.setEM(em);        
        
    }


//    @Test
    public void testAddClub() throws Exception {
        clubDao.addClub(club1);
        clubDao.addClub(club2);
    }

//    @Test
    public void testRemoveClub() {
        clubDao.removeClub("testClub2");
    }

//    @Test
    public void testGetClubs() {
        List<Club> expected = new ArrayList<Club>();
        expected.add(club1);
        
        assertEquals("There should be only testClub1",
                expected, clubDao.getClubs());
    }

//    @Test
    public void testFindClubByName() {
        assertEquals(club1, clubDao.findClubByName("testClub1"));
    }

//    @Test
    public void testFindClubByTeam() {
        assertEquals(club1, clubDao.findClubByTeam("testTeam1"));
    }
    
    
    
    /* PRIVATE OPS */
    
    private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }

    private void setUpClubs() {        
        club1 = new Club("testClub1");
        club1.setEmail("test1@mail.com");
        List<Team> teams1 = new ArrayList<Team>();
        teams1.add(new Team("testTeam1"));
        club1.setTeams(teams1);
        
        club2 = new Club("testClub2");
        club2.setEmail("test2@mail.com");
        List<Team> teams2 = new ArrayList<Team>();
        teams2.add(new Team("testTeam2"));
        club2.setTeams(teams2);
        
    }
}