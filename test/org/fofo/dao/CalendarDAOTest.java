package org.fofo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.fofo.entity.*;

/**
 *
 * @author David Hernández
 * @author Anton Urrea
 */
@RunWith(JMock.class)
public class CalendarDAOTest {

    Mockery context = new JUnit4Mockery();
    EntityManager em;
    EntityTransaction transaction;
    CalendarDAOImpl calDAO;
    CalendarDAO caldao;
    Match match1, match2, match3, match4;
    WeekMatch wm1, wm2;
    FCalendar cal;
    Club club;
    Team team1, team2, team3, team4;
    private Competition comp;

    @Before
    public void setUp() throws Exception {
        
        em = context.mock(EntityManager.class);
        transaction = context.mock(EntityTransaction.class);
        caldao = context.mock(CalendarDAO.class);

        calDAO = new CalendarDAOImpl();
        calDAO.setEm(em);
        
        club = new Club("ClubExemple");
        club.setEmail("exemple@hotmail.com");

        team1 = new Team("Team1", Category.FEMALE);
        team1.setClub(club);
        team1.setEmail("Team1@hotmail.com");

        team2 = new Team("Team2", Category.FEMALE);
        team2.setClub(club);
        team2.setEmail("Team2@hotmail.com");

        team3 = new Team("Team3", Category.FEMALE);
        team3.setClub(club);
        team3.setEmail("Team3@hotmail.com");

        team4 = new Team("Team4", Category.VETERAN);
        team4.setClub(club);
        team4.setEmail("Team4@hotmail.com");
        
       
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

        wm1 = new WeekMatch();
        wm1.addMatch(match1);
        wm1.addMatch(match2);
        
        wm2 = new WeekMatch();
        wm2.addMatch(match3);
        wm2.addMatch(match4);

 
        comp = Competition.create(CompetitionType.CUP);
        comp.setCategory(Category.FEMALE);
        comp.setInici(new Date()); 
        comp.setMaxTeams(4);
        comp.setMinTeams(2);
        comp.setName("Lleida");
        
        cal = new FCalendar();     
        cal.setCompetition(comp);
        
    }

    
    @Test
    public void testAddCalendar() throws Exception{
       
        final List<WeekMatch> wm = new ArrayList<WeekMatch>();
        wm.add(wm1);
        wm.add(wm2);   
        
        cal.setWeekMatches(wm);
        
        context.checking(new Expectations() {

            {
                oneOf(caldao).addCalendar(cal);  
            }
        });

        caldao.addCalendar(cal);
       
    }
    

}