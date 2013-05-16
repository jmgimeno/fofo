package org.fofo.entity;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jnp2
 */
public class CalendarCupTest {
    CalendarCupGen generator;
    Competition comp;  
    Club club;
    
    @Before
    public void setUp() throws InvalidRequisitsException {
        comp = Competition.create(Type.CUP);
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
        generator = new CalendarCupGen(comp);  
        generator.calculateCalendar();
    }    
    
    @Test   
    public void testNumWeekMatchesException()  throws Exception {
        generator = new CalendarCupGen(comp);        
        FCalendar calendar = generator.calculateCalendar();
        int nj = calendar.getNumOfWeekMatches();
        assertEquals(4,nj);  
    }
    
    
    @Test   
    public void testNumMatchesInFirstWeek()  throws Exception {
        generator = new CalendarCupGen(comp);        
        FCalendar calendar = generator.calculateCalendar();
        
        WeekMatches first = calendar.getWeekMatch(0);
        List<Match> listMatch = first.getListOfWeekMatches();

        assertTrue(listMatch.size()==8);
    }
    
    @Test   
    public void testNumMatchesInEachWeekMatches()  throws Exception {
        generator = new CalendarCupGen(comp); 
        FCalendar calendar = generator.calculateCalendar();
  
        int nmatches = 8;
        for(int i=0; i<4; i++){
            assertTrue(assertEqualsMatch(calendar.getWeekMatch(i),nmatches));
            nmatches = nmatches/2;
        }        
    }      
    
    @Test   
    public void testIfAllTeamsParticipatingInFirstWeekMatchesAreCorrect()  throws Exception {
        generator = new CalendarCupGen(comp);        
        FCalendar calendar = generator.calculateCalendar();
        
        WeekMatches first = calendar.getWeekMatch(0);
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
        generator = new CalendarCupGen(comp);        
        FCalendar calendar = generator.calculateCalendar();
        
        WeekMatches first = calendar.getWeekMatch(0);
        List<Match> listMatch = first.getListOfWeekMatches();     
        List<Team> listTeams = comp.getTeams();      
       
        assertTrue(allTeamsPlayInOneMatch(listTeams, listMatch));
    }    
    
    @Test   
    public void testTeamsParticipatingInSecondWeekMatch()  throws Exception {
        generator = new CalendarCupGen(comp);        
        FCalendar calendar = generator.calculateCalendar();
        
        WeekMatches second = calendar.getWeekMatch(1);
        List<Match> listMatch = second.getListOfWeekMatches();     
        List<Team> listTeams = generateTeams(1,8);  
        
        assertTrue(allTeamsPlayInOneMatch(listTeams, listMatch));
    }     
    
    @Test   
    public void testTeamsParticipatingInEachWeekMatch()  throws Exception {
        generator = new CalendarCupGen(comp);        
        FCalendar calendar = generator.calculateCalendar();
        
        int numTeams = 8;
        for(int i=1; i<=3; i++){
            WeekMatches second = calendar.getWeekMatch(i);
            List<Match> listMatch = second.getListOfWeekMatches();   
            List<Team> listTeams = generateTeams(i,numTeams);
            numTeams /=2;
            assertTrue(allTeamsPlayInOneMatch(listTeams, listMatch));
        }
    }      
       
    
    private boolean assertEqualsMatch(WeekMatches match, int nmatches){
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
