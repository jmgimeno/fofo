package org.fofo.services.management;

import org.fofo.services.management.exception.NumberOfTeamsException;
import org.fofo.services.management.exception.IncorrectCompetitionTypeException;
import org.fofo.services.management.exception.UnknownCompetitionTypeException;
import org.fofo.services.management.exception.MinimumDaysException;
import java.util.List;
import org.fofo.entity.*;
import org.joda.time.DateTime;

/**
 * @author Jordi, Anatoli
 */
public abstract class CalendarGen {    
    
    private static int MINIMUM_DAYS = 7;
       
//    public abstract FCalendar calculateCalendar() throws Exception;  
    /**
     *
     * @param comp
     * @return
     * @throws Exception
     */
    public abstract FCalendar calculateCalendar(Competition comp) throws Exception; 

            
    /**
     *
     * @param comp
     * @throws Exception
     */
    public static void checkRequirements(Competition comp) throws Exception {
            
       if(!isLeagueCompetition(comp) && !isCupCompetition(comp)) 
            throw new UnknownCompetitionTypeException();                                                                 
       
       if(comp instanceof CompetitionLeague && !isLeagueCompetition(comp))
           throw new IncorrectCompetitionTypeException();
       
       if(comp instanceof CompetitionCup && !isCupCompetition(comp))
           throw new IncorrectCompetitionTypeException();
       
        if(!minimDaysPassed(comp)) 
            throw new MinimumDaysException("It must be a difference of 7 days as "
                    + "minimum betwen Calendar creation and Competition creation");       
        
        if(!teamsRequired(comp))
            throw new NumberOfTeamsException("This "
                    +comp.getType().toString()+" competition has not "
                    +"the right number of teams betwen "
                    +comp.getMinTeams()+" and " +comp.getMaxTeams()); 
    }     

    private static boolean minimDaysPassed(Competition comp){    
        DateTime actual = new DateTime();
        DateTime compDate = new DateTime(comp.getInici());
       return (actual.getDayOfYear() - compDate.getDayOfYear()) >= MINIMUM_DAYS;
    }   
    
    private static boolean teamsRequired(Competition comp){
        List<Team> list = comp.getTeams();      
        
        return list.size()>=comp.getMinTeams() 
                && list.size()<=comp.getMaxTeams();
    }
    
    private static boolean isCupCompetition(Competition comp){
        return comp.getType() == CompetitionType.CUP;
    }
    
    private static boolean isLeagueCompetition(Competition comp){
        return comp.getType() == CompetitionType.LEAGUE;
    }
    
}
