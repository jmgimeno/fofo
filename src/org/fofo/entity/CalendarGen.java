package org.fofo.entity;

import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

/**
 * @author Jordi, Anatoli
 */
public class CalendarGen {
    private Competition competition;   
    private CalendarGen generator;
    
    public CalendarGen(){       
        this.competition= null;  
    }
    
    public CalendarGen(Competition competition) throws Exception{
        this.competition= competition;       
    }
    
    public FCalendar CalculateCalendar() throws Exception{                
        checkRequiriments();        
        if(isLeagueCompetition()) return CalculateCalendarLeague();
        else return CalculateCalendarCup();
    }
    
    public FCalendar CalculateCalendarCup() throws Exception{     
        checkRequiriments();        
        if(!isCupCompetition())throw new Exception();
        
        generator = new CalendarCupGen(competition);
        return generator.CalculateCalendar();           
    }
    
    public FCalendar CalculateCalendarLeague() throws Exception{    
        checkRequiriments();        
        if(!isLeagueCompetition())throw new Exception();
        
        generator = new CalendarLeagueGen(competition);
        return generator.CalculateCalendar();
    }    
    

    public Competition getCompetition(){
        return this.competition;
    }
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
    
    private void checkRequiriments() throws Exception {
        if(competition == null) throw new UnknownCompetitionTypeException();
        
       if(!isLeagueCompetition() && !isCupCompetition())
            throw new UnknownCompetitionTypeException();
       
        if(!minimDaysPassed()) 
            throw new MinimumDaysException("It must be a difference of 7 days as "
                    + "minimum betwen Calendar creation and Competition creation");       
        
        if(!teamsRequired())
            throw new NumberOfTeamsException("This "
                    +competition.getType().toString()+" competition has not "
                    +"the right number of teams betwen "
                    +competition.getMinTeams()+" and " +competition.getMaxTeams()); 
    }     

    private boolean minimDaysPassed(){    
        DateTime actual = new DateTime();
        DateTime compDate = new DateTime(competition.getInici());
       return (actual.getDayOfYear() - compDate.getDayOfYear()) >= 7;
    }   
    
    private boolean teamsRequired(){
        List<Team> list = competition.getTeams();      
        
        return list.size()>=competition.getMinTeams() 
                && list.size()<=competition.getMaxTeams();
    }
    
    private boolean isCupCompetition(){
        return competition.getType() == Type.CUP;
    }
    
    private boolean isLeagueCompetition(){
        return competition.getType() == Type.LEAGUE;
    }
    
}
