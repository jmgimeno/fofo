package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.fofo.dao.*;
import org.fofo.entity.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Jordi Niubo i Oriol Capell
 */

public class TeamManagementServiceIntegTest {
    
    ManagementService service;
    TeamDAOImpl teamDao;
    ClubDAOImpl clubDao;
    Team team;
    Club club;
    EntityManager em;
    
    public TeamManagementServiceIntegTest() {
    }

    @Before
    public void setUp() throws Exception {
        service = new ManagementService();  
        teamDao  = new TeamDAOImpl();
        clubDao  = new ClubDAOImpl();
        
        em = getEntityManager();
        clubDao.setEM(em);
        teamDao.setEM(em);
        
        service.setTeamDao(teamDao);
        service.setClubDao(clubDao);
        team = new Team("Team 1");
        club = new Club("Club 1");
        club.setEmail("email@email.com");
    }


    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithNoValues() throws Exception{
        service.addTeam(team);
    }
    
    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithOnlyEmail() throws Exception{
        team.setEmail("email@email.com");                
        service.addTeam(team);
    }  
    
    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithOnlyCategory() throws Exception{
        team.setCategory(Category.MALE);              
        service.addTeam(team);
    }     
    
    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithOnlyClub() throws Exception{
        team.setClub(club);            
        service.addTeam(team);
    }
    
    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithNoEmail() throws Exception{
        team.setCategory(Category.MALE);  
        team.setClub(club);                
        service.addTeam(team);
    } 
    
    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithNoClub() throws Exception{
        team.setCategory(Category.MALE);  
        team.setEmail("email@email.com");                  
        service.addTeam(team);
    }   
    
    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithNoCategory() throws Exception{
        team.setCategory(Category.MALE);  
        team.setClub(club);                   
        service.addTeam(team);
    }
    
    @Test(expected=InscriptionTeamException.class)
    public void testInscriptionTeamWithoutClubInDB() throws Exception{
        team.setCategory(Category.MALE);  
        team.setClub(club); 
        team.setEmail("email@email.com");  
        
        service.addTeam(team);
        
        Team teamDB = teamDao.findTeamByName(team.getName());
        assertEquals("Team should be equal", teamDB, team);
    }   
    
    
    @Test
    public void testCorrectInscriptionTeam() throws Exception{
        clubDao.addClub(club);        
        team.setCategory(Category.MALE);  
        team.setClub(club); 
        team.setEmail("email@email.com");  
        
        service.addTeam(team);
        
        Team teamDB = teamDao.findTeamByName(team.getName());
        assertEquals("Team should be equal", teamDB, team);
    }    
    
    /*
   * PRIVATE OPS
   * 
   * 
   */
    
     private EntityManager getEntityManager() throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fofo");
        return emf.createEntityManager();  
    }
}
