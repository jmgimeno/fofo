package org.fofo.services.management;

import org.fofo.dao.*;
import org.fofo.dao.MatchDAO;
import org.fofo.entity.*;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 *
 * @author jnp2
 */

@RunWith(JMock.class)
public class AssignRefereesToCompetitionTest {
    AssignReferees  service;   
    Mockery context = new JUnit4Mockery();
    MatchDAO matchDao;
    RefereeDAO refereeDao;
    Competition comp;   
    WeekMatch wm1, wm2, wm3, wm4;  
    Match match1, match2, match3, match4;
    Team team1, team2, team3, team4;
    
    public AssignRefereesToCompetitionTest() {
    }

    @Before
    public void setUp() throws Exception {     
        service = new AssignReferees();        
        matchDao = context.mock(MatchDAO.class);    
        refereeDao = context.mock(RefereeDAO.class);     
        
        comp = Competition.create(CompetitionType.LEAGUE);        
        createCompetition();
    }


    @Test
    public void testSomeMethod() {
    }

    
    
    
    
    
    
    
    
    /*
     * 
     * PRIVATE OPERATIONS
     * 
     */
    private void createCompetition() {   
        comp.setName("Competition 1");
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);    
        comp.setInici(new DateTime().minusDays(8).toDate());         

        createTeams();
        createWeekmatch();    
    }

    private void createTeams() {
        team1 = new Team("Team1", Category.FEMALE);
        team1.setEmail("Team1@hotmail.com");
        comp.addTeam(team1);

        team2 = new Team("Team2", Category.FEMALE);
        team2.setEmail("Team2@hotmail.com");
        comp.addTeam(team2);
        
        team3 = new Team("Team3", Category.FEMALE);
        team3.setEmail("Team3@hotmail.com");
        comp.addTeam(team3);
        
        team4 = new Team("Team4", Category.FEMALE);
        team4.setEmail("Team4@hotmail.com");  
        comp.addTeam(team4);
    }



    private void createWeekmatch() {
        createMatchs();
        
        wm1 = new WeekMatch();
        wm1.addMatch(match1);

        wm2 = new WeekMatch();
        wm2.addMatch(match2);
        wm2.addMatch(match3);

        wm3 = new WeekMatch();
        wm3.addMatch(match3);
        wm3.addMatch(match4);

        wm4 = new WeekMatch();
    }
    
    private void createMatchs() {
        match1 = new Match();
        match1.setHome(team1);
        match1.setVisitor(team2);

        match2 = new Match();
        match2.setHome(team3);
        match2.setVisitor(team4);

        match3 = new Match();
        match3.setHome(team2);
        match3.setVisitor(team1);

        match4 = new Match();
        match4.setHome(team4);
        match4.setVisitor(team3);
    }
    
}