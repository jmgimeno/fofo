/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.dao;

import org.fofo.dao.exception.AlreadyExistingRefereeException;
import org.fofo.dao.exception.NotAssignedMatchToRefereeException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.fofo.entity.Club;
import org.fofo.entity.Match;
import org.fofo.entity.Referee;
import org.fofo.entity.Team;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

/**
 *
 * @author Anatoli, Mohamed
 */
public class RefereeDAOImplIntegTest {
    
    private MatchDAOImpl mDao;
    private TeamDAOImpl teamDao;
    private ClubDAOImpl clubDao;
    
    private RefereeDAOImpl refDao;
    private EntityManager em;
    private Referee referee;
    private Match match;
    
    
    public RefereeDAOImplIntegTest() {
    }
    
    @Before
    public void setUp() throws Exception{
        em = getEntityManager();
        refDao = new RefereeDAOImpl();
        refDao.setEM(em);
        
        clubDao = new ClubDAOImpl();
        clubDao.setEM(em);
        
        teamDao = new TeamDAOImpl();
        teamDao.setEM(em);
        
        mDao= new MatchDAOImpl();
        mDao.setEm(em);
        mDao.setRefereedb(refDao);
        
        setUpClubsTeamsMatches();
        
        referee = new Referee("refereeNif","refereeName");
        referee.setEmail("referee@mail.com");
        
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
    public void testFindRefereeByMatch() throws Exception{
       
        refDao.addReferee(referee);

        mDao.addRefereeToMatch(match.getIdMatch(),referee.getNif());        
        
                
        assertEquals(referee,refDao.findRefereeByMatch(match.getIdMatch()));
    }
    
    @Test (expected = NotAssignedMatchToRefereeException.class) 
    public void notAssignedMatchToReferee() throws Exception{
       
        
        refDao.addReferee(referee);               
                
        refDao.findRefereeByMatch(match.getIdMatch());
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
        Query query2=em.createQuery("DELETE FROM Match"); 
        Query query3=em.createQuery("DELETE FROM Team");
        Query query4=em.createQuery("DELETE FROM Club");
        int deleteRecords=query.executeUpdate();     
        deleteRecords=query2.executeUpdate();     
        deleteRecords=query3.executeUpdate(); 
        deleteRecords=query4.executeUpdate(); 

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

    private void setUpClubsTeamsMatches() throws Exception{
        Club club = new Club("testClub");
        Team home = new Team("home"); home.setClub(club);
        Team visitor = new Team("visitor"); visitor.setClub(club);
        match = new Match(home,visitor);        

        clubDao.addClub(club);
        teamDao.addTeam(home);
        teamDao.addTeam(visitor);
        mDao.insertMatch(match);
    }
}