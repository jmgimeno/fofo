package org.fofo.services.management;

import java.util.Date;
import java.util.List;
import org.fofo.dao.*;
import org.fofo.entity.Competition;
import org.fofo.entity.CompetitionCup;
import org.fofo.entity.CompetitionLeague;
import org.fofo.entity.FCalendar;
import org.fofo.entity.Team;
import org.fofo.entity.Type;
import org.joda.time.DateTime;

/**
 * @author Jordi, Anatoli
 */
public abstract class CalendarGen {    
       
//    public abstract FCalendar calculateCalendar() throws Exception;  
    public abstract FCalendar calculateCalendar(Competition comp) throws Exception; 

            
    public void checkRequirements(Competition comp) throws Exception {
            
       if(!isLeagueCompetition(comp) && !isCupCompetition(comp)) 
            throw new UnknownCompetitionTypeException();                                                                 
       
       if(comp instanceof CompetitionLeague && !isLeagueCompetition(comp))
           throw new UnknownCompetitionTypeException();
       
       if(comp instanceof CompetitionCup && !isCupCompetition(comp))
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
