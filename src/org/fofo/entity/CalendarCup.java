package org.fofo.entity;

import java.util.Date;
import java.util.List;
import org.fofo.entity.Competition;
import org.fofo.entity.InvalidRequisitsException;
import org.fofo.entity.Team;

/**
 *
 * @author Jordi i Oriol
 */
public class CalendarCup {
    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisecons/day 
    final Date init = new Date();
    private final Competition competition;
    
    public CalendarCup(Competition competition) throws InvalidRequisitsException{
        this.competition = competition;
        if(minimDaysPassed()) throw new InvalidRequisitsException();
        if(!teamsRequired())throw new InvalidRequisitsException();
        if(!isPotencyOfTwo())throw new InvalidRequisitsException();        
        
        
        
    }
    
    private boolean minimDaysPassed(){    
        long difference = 
             (init.getTime() - competition.getInici().getTime() )/MILLSECS_PER_DAY;
        if(difference < 7) return false;
        return true;
    }    

    private boolean teamsRequired(){
        List<Team> list = competition.getTeams();
        if(list.size()>competition.getMinTeams() && 
           list.size()<competition.getMaxTeams()){
            return true;
        }
        return false;            
    }    
    
    private boolean isPotencyOfTwo(){ 
        int num = competition.getTeams().size();
        while (num >= 2) {
            if (num % 2 != 0) {
                return false;
            }
            num /= 2;
        }
        return true;
    }
 
}
