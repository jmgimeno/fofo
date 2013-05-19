package org.fofo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.fofo.entity.Category;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.Team;
import org.fofo.entity.WeekMatches;
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
    WeekMatches wm1, wm2;
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
        match1.setLocal(team1);
        match1.setVisitant(team2);
        
        match2 = new Match();
        match2.setLocal(team3);
        match2.setVisitant(team4);
        
        match3 = new Match();
        match3.setLocal(team2);
        match3.setVisitant(team1);
        
        match4 = new Match();
        match4.setLocal(team4);
        match4.setVisitant(team3);

        wm1 = new WeekMatches();
        wm1.addMatch(match1);
        wm1.addMatch(match2);
        
        wm2 = new WeekMatches();
        wm2.addMatch(match3);
        wm2.addMatch(match4);

 
        comp = Competition.create(Type.CUP);
        comp.setCategory(Category.FEMALE);
        comp.setInici(new Date()); 
        comp.setMaxTeams(4);
        comp.setMinTeams(2);
        comp.setName("Lleida");
        
        cal = new FCalendar();     
        cal.setCompetition(comp);
        
    }

    
    @Test
    public void testAddCalendar(){
       
        final List<WeekMatches> wm = new ArrayList<WeekMatches>();
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
    
        
    @Test
    public void testFindCalendar(){
       
        final List<WeekMatches> wm = new ArrayList<WeekMatches>();
        wm.add(wm1);
        wm.add(wm2);   
        
        cal.setWeekMatches(wm);
        
        context.checking(new Expectations() {

            {
                oneOf(caldao).findCalendar(cal);
            }
        });

        caldao.findCalendar(cal);
       
    }
    

}