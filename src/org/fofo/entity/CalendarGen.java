package org.fofo.entity;

import java.util.Date;
import java.util.List;

/**
 * @author jnp2
 */
public class CalendarGen {
    private Competition competition;
    private CalendarGen generator;
    
    public CalendarGen(){        
    }
    public CalendarGen(Competition competition){
        this.competition= competition;
    }
    public FCalendar CalculateCalendar() throws InvalidRequisitsException, NonUniqueIdException,
                       TeamCanPlayOnlyOneMatchForAWeekException, UnknownCompetitionTypeException{ 
        checkRequiriments();
        
        if(competition.getType()==Type.LEAGUE) generator = new CalendarLeagueGen();
        else if(competition.getType()==Type.CUP) generator = new CalendarCupGen();

        generator.setCompetition(competition);
        return generator.CalculateCalendar();
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    private void checkRequiriments() throws UnknownCompetitionTypeException, InvalidRequisitsException {
        if(competition == null) throw new UnknownCompetitionTypeException();
        if(!minimDaysPassed()) throw new InvalidRequisitsException();       
        if(!teamsRequired())throw new InvalidRequisitsException(); 
    }   
    
    //Refactor
    private boolean minimDaysPassed(){    
        //Need to implement with Anatoli's form
        long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisecons/day 
        Date init = new Date();
        long difference = 
             (init.getTime() - competition.getInici().getTime() )/MILLSECS_PER_DAY;
        if(difference < 7) return false;   
        return true;
    }    

    //Refactor
    private boolean teamsRequired(){
        List<Team> list = competition.getTeams();      
        if(list.size()>=competition.getMinTeams() && 
           list.size()<=competition.getMaxTeams()){
            return true;
        }
        return false;            
    }
    
}
