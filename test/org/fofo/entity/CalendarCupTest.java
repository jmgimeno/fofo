/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fofo.entity;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jnp2
 */
public class CalendarCupTest {
    CalendarCup calendar;
    Competition comp;
    
    public CalendarCupTest() throws InvalidRequisitsException {
        comp = new Competition();
        comp.setCategory(Category.MALE);
        comp.setInici(null);
        comp.setMaxTeams(16);
        comp.setMinTeams(4);
        comp.setType(Type.CUP);
        List<Team> list = new ArrayList<Team>();
        for(int i=0; i<16;i++){
            list.add(new Team("Team number "+i));
        }
        comp.setTeams(list);
        
        int año = 2013; int mes = 4; int dia = 22; //Fecha anterior 
        GregorianCalendar cal = new GregorianCalendar(año, mes-1, dia);
        Date fecha = new java.sql.Date(cal.getTimeInMillis());
        comp.setInici(fecha);         
    }



    @Test(expected=InvalidRequisitsException.class)
    public void testDateException() throws InvalidRequisitsException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException {
        comp.setInici(new Date());
        calendar = new CalendarCup(comp);        
    }
    
    @Test(expected=InvalidRequisitsException.class)    
    public void testMinTeamsException() throws InvalidRequisitsException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException {
        comp.setMinTeams(20);
        calendar = new CalendarCup(comp);        
    }   
    
    @Test(expected=InvalidRequisitsException.class)    
    public void testMaxTeamsException() throws InvalidRequisitsException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
        comp.setMaxTeams(10);
        calendar = new CalendarCup(comp);        
    }      
    
    @Test   
    public void testNumWeekMatchesException() throws InvalidRequisitsException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{
        calendar = new CalendarCup(comp); 
        int nj = calendar.getNumWeekMatches();
        assertEquals(4,nj);  
    }
    
    
    @Test   
    public void testIfAllTeamsParticipateInFirstWeekMatches() throws InvalidRequisitsException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{  
        calendar = new CalendarCup(comp); 
        Map<Integer,WeekMatches> weekMatches = calendar.getWeekMatches();
        WeekMatches first = weekMatches.get(1);
        List<Match> listMatch = first.getListOfWeekMatches();
        List<Team> expected = comp.getTeams();
        
        if(listMatch.size()*2 != 16) fail();
         
        for(Match match : listMatch){
            Team local = match.getLocal();
            Team visitant = match.getVisitant();
            if(!expected.contains(local) && !expected.contains(visitant)){
                fail();
            }
        }  
    }
      
    @Test   
    public void testNumMatchesInEachWeekMatches() throws InvalidRequisitsException, NonUniqueIdException, TeamCanPlayOnlyOneMatchForAWeekException{ 
        calendar = new CalendarCup(comp); 
        Map<Integer,WeekMatches> list = calendar.getWeekMatches();
        int numWM = 4;
        for(int i=1; i<numWM; i++){
            int numMFirst = list.get(i).getNumberOfMatchs();
            int numMSecond = list.get(i+1).getNumberOfMatchs();
            if(numMFirst/2 != numMSecond){
                fail();
            }        
        }       
    }    
    
        
        
    }    
