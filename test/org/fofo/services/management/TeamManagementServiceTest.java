package org.fofo.services.management;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.fofo.dao.ClubDAO;
import org.fofo.dao.CompetitionDAO;
import org.fofo.dao.TeamDAO;
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
 * @author jnp2
 */
public class TeamManagementServiceTest {
    
    ManagementService service;
    Mockery context = new JUnit4Mockery();
    TeamDAO teamDao;
    ClubDAO clubDao;
    Team team;
    Club club;
    
    public TeamManagementServiceTest() {
    }

    @Before
    public void setUp() throws Exception {
        service = new ManagementService();  
        teamDao  = context.mock(TeamDAO.class);
        clubDao  = context.mock(ClubDAO.class);
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
    
    @Test
    public void testCorrectInscriptionTeam() throws Exception{
        final List<Club> list = new ArrayList<Club>();
        list.add(club);
        context.checking(new Expectations() {{
            oneOf (clubDao).getClubs();will(returnValue(list));
            oneOf (teamDao).addTeam(team);
        }});  
        
        team.setCategory(Category.MALE);  
        team.setClub(club); 
        team.setEmail("email@email.com");                    
        service.addTeam(team);
    }    
    
}
