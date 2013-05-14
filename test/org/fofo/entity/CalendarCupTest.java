package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
    CalendarGen generator;
    Competition comp;
    
    @Before
    public void setUp() throws InvalidRequisitsException {
        comp = Competition.create(Type.CUP);
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);
        Club club = new Club();
        club.setName("AA");
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
       // assertEquals(4,nj);  
    }
    
    
    @Test   
    public void testIfAllTeamsParticipateInFirstWeekMatches()  throws Exception {
        generator = new CalendarCupGen(comp);        
        FCalendar calendar = generator.calculateCalendar();
        
        WeekMatches first = calendar.getWeekMatch(0);
        List<Match> listMatch = first.getListOfWeekMatches();
//expected == listTeams        
        List<Team> expected = comp.getTeams();
        
//Dividir en 2 tests        
        if(listMatch.size()*2 != 16) fail();
         
//Assert         
        for(Match match : listMatch){
            Team local = match.getLocal();
            Team visitant = match.getVisitant();
//assertTrue(listTeams.contains(local));
//assertTrue(listTeams.contains(visitant));            
            if(!expected.contains(local) || !expected.contains(visitant)){
                fail();
            }
        }          
    }
    
//Test que tots els nostres equips juguen un cop
//Test que els equips nomes estiguin jugan 1 cop    
//En cap jornada cap equip juga més d'un partit
//Els partits en cada ronda    
      
    @Test   
    public void testNumMatchesInEachWeekMatches()  throws Exception {
        generator = new CalendarCupGen(comp); 
        FCalendar calendar = generator.calculateCalendar();
  
        int nmatches = 8;
        for(int i=0; i<4; i++){
            assertTrue(assertEquals(calendar.getWeekMatch(i),nmatches));
            nmatches = nmatches/2;
        }        
    }    
    
    private boolean assertEquals(WeekMatches match, int nmatches){
        if(match.getNumberOfMatchs() == nmatches) return true;
        else return false;
    }
    
    
}    
