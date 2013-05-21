package org.fofo.services.management;

import org.fofo.services.management.InvalidRequisitsException;
import org.fofo.services.management.MinimumDaysException;
import org.fofo.services.management.CalendarCupGen;
import java.util.ArrayList;
import java.util.List;
import org.fofo.entity.Category;
import org.fofo.entity.Club;
import org.fofo.entity.Competition;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Match;
import org.fofo.entity.Team;
import org.fofo.entity.CompetitionType;
import org.fofo.entity.WeekMatch;
import org.joda.time.DateTime;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jnp2
 */
public class CalendarCupGenTest {
    CalendarCupGen generator;
    Competition comp;  
    Club club;
    
    @Before
    public void setUp() throws InvalidRequisitsException {
        comp = Competition.create(CompetitionType.CUP);
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);
        club = new Club();
        club.setName("Imaginary club");
        List<Team> list = new ArrayList<Team>();
        for(int i=0; i<16;i++){
            list.add(new Team("Team number "+i,club, Category.MALE));
        }
        comp.setTeams(list);
        comp.setInici(new DateTime().minusDays(8).toDate());        
    }
    

    @Test(expected=MinimumDaysException.class)
    public void testDateException() throws Exception {
        comp.setInici(new DateTime().minusDays(6).toDate());
        generator = new CalendarCupGen();  
        generator.calculateCalendar(comp);
    }    
    
    @Test   
    public void testNumWeekMatchesException()  throws Exception {
        generator = new CalendarCupGen();        
        FCalendar calendar = generator.calculateCalendar(comp);
        int nj = calendar.getNumOfWeekMatches();
        assertEquals(4,nj);  
    }
    
    
    @Test   
    public void testNumMatchesInFirstWeek()  throws Exception {
        generator = new CalendarCupGen();        
        FCalendar calendar = generator.calculateCalendar(comp);
        
        WeekMatch first = calendar.getWeekMatch(0);
        List<Match> listMatch = first.getListOfWeekMatches();

        assertTrue(listMatch.size()==8);
    }
    
    @Test   
    public void testNumMatchesInEachWeekMatches()  throws Exception {
        generator = new CalendarCupGen(); 
        FCalendar calendar = generator.calculateCalendar(comp);
  
        int nmatches = 8;
        for(int i=0; i<4; i++){
            assertTrue(assertEqualsMatch(calendar.getWeekMatch(i),nmatches));
            nmatches = nmatches/2;
        }        
    }      
    
    @Test   
    public void testIfAllTeamsParticipatingInFirstWeekMatchesAreCorrect()  throws Exception {
        generator = new CalendarCupGen();        
        FCalendar calendar = generator.calculateCalendar(comp);
        
        WeekMatch first = calendar.getWeekMatch(0);
        List<Match> listMatch = first.getListOfWeekMatches();     
        List<Team> listTeams = comp.getTeams();      
        
        for(Match match : listMatch){
            Team local = match.getLocal();
            Team visitant = match.getVisitant();
            assertTrue(listTeams.contains(local));
            assertTrue(listTeams.contains(visitant));  
        }
    }
    
    @Test   
    public void testIfOurTeamsParticipatingInFirstWeekMatch()  throws Exception {
        generator = new CalendarCupGen();        
        FCalendar calendar = generator.calculateCalendar(comp);
        
        WeekMatch first = calendar.getWeekMatch(0);
        List<Match> listMatch = first.getListOfWeekMatches();     
        List<Team> listTeams = comp.getTeams();      
       
        assertTrue(allTeamsPlayInOneMatch(listTeams, listMatch));
    }    
    
    @Test   
    public void testTeamsParticipatingInSecondWeekMatch()  throws Exception {
        generator = new CalendarCupGen();        
        FCalendar calendar = generator.calculateCalendar(comp);
        
        WeekMatch second = calendar.getWeekMatch(1);
        List<Match> listMatch = second.getListOfWeekMatches();     
        List<Team> listTeams = generateTeams(1,8);  
        
        assertTrue(allTeamsPlayInOneMatch(listTeams, listMatch));
    }     
    
    @Test   
    public void testTeamsParticipatingInEachWeekMatch()  throws Exception {
        generator = new CalendarCupGen();        
        FCalendar calendar = generator.calculateCalendar(comp);
        
        int numTeams = 8;
        for(int i=1; i<=3; i++){
            WeekMatch second = calendar.getWeekMatch(i);
            List<Match> listMatch = second.getListOfWeekMatches();   
            List<Team> listTeams = generateTeams(i,numTeams);
            numTeams /=2;
            assertTrue(allTeamsPlayInOneMatch(listTeams, listMatch));
        }
    }      
       
    
    private boolean assertEqualsMatch(WeekMatch match, int nmatches){
        if(match.getNumberOfMatchs() == nmatches) return true;
        else return false;
    }

    private List<Team> generateTeams(int round, int teams) {
        List<Team> listTeam  = new ArrayList<Team>(); 
        for(int i=1; i<=teams;i++){
            Team team = new Team("Winer match "+i+" of round "+round,club, Category.MALE);
            listTeam.add(team);            
        } 
        return listTeam;
    }

    private boolean allTeamsPlayInOneMatch(List<Team> listTeams, List<Match> listMatch) {
        int numTeams = listTeams.size();
        for(Match match : listMatch){
            Team local = match.getLocal();
            Team visitant = match.getVisitant();
            listTeams.remove(local);
            listTeams.remove(visitant); 
        }
        return listTeams.isEmpty() && listMatch.size()*2==numTeams;
    }
    
    
}    
