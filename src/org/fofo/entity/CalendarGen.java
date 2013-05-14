package org.fofo.entity;

import java.util.Date;
import java.util.List;
import org.fofo.dao.*;
import org.joda.time.DateTime;

/**
 * @author Jordi, Anatoli
 */
public abstract class CalendarGen {    
        
    public abstract FCalendar calculateCalendar() throws Exception;               
//        checkRequiriments();           
//        if(isLeagueCompetition()) return CalculateCalendarLeague();
//        else return CalculateCalendarCup();
//    }
//    
//    public FCalendar CalculateCalendarCup() throws Exception{     
//        checkRequiriments();        
//        if(!isCupCompetition()) throw new Exception();        
//        generator = new CalendarCupGen(competition);
//        return generator.CalculateCalendar();           
//    
    
//    public FCalendar CalculateCalendarLeague() throws Exception{    
//        checkRequiriments();      
//        if(!isLeagueCompetition()) throw new Exception();
//        generator = new CalendarLeagueGen(competition);
//        return generator.CalculateCalendar();
//    }    

            
    public void checkRequirements(Competition comp) throws Exception {
            
       if(!isLeagueCompetition(comp) && !isCupCompetition(comp)) 
            throw new UnknownCompetitionTypeException();                                                                 
       
        if(!minimDaysPassed(comp)) 
            throw new MinimumDaysException("It must be a difference of 7 days as "
                    + "minimum betwen Calendar creation and Competition creation");       
        
        if(!teamsRequired(comp))
            throw new NumberOfTeamsException("This "
                    +comp.getType().toString()+" competition has not "
                    +"the right number of teams betwen "
                    +comp.getMinTeams()+" and " +comp.getMaxTeams()); 
    }     

    private boolean minimDaysPassed(Competition comp){    
        DateTime actual = new DateTime();
        DateTime compDate = new DateTime(comp.getInici());
       return (actual.getDayOfYear() - compDate.getDayOfYear()) >= 7;
    }   
    
    private boolean teamsRequired(Competition comp){
        List<Team> list = comp.getTeams();      
        
        return list.size()>=comp.getMinTeams() 
                && list.size()<=comp.getMaxTeams();
    }
    
    private boolean isCupCompetition(Competition comp){
        return comp.getType() == Type.CUP;
    }
    
    private boolean isLeagueCompetition(Competition comp){
        return comp.getType() == Type.LEAGUE;
    }
    
}
