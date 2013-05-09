package org.fofo.entity;

import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

/**
 * @author Jordi, Anatoli
 */
public abstract class CalendarGen {
    private Competition competition;   
    //private CalendarGen generator;
    
    public CalendarGen(){        
    }
    
    public CalendarGen(Competition competition) throws Exception{
        this.competition= competition;
        checkRequiriments();
        
    }
    
    public abstract FCalendar CalculateCalendar() throws Exception;
//    { 
//        checkRequiriments();
//        
//        if(isLeagueCompetition()) generator = new CalendarLeagueGen();
//        else if(isCupCompetition()) generator = new CalendarCupGen();
//
//        generator.setCompetition(competition); 
//        return generator.CalculateCalendar();
//    }

    public Competition getCompetition(){
        return this.competition;
    }
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    
    /* PRIVATE OPS */
    
    private void checkRequiriments() throws Exception {
        if(!isLeagueCompetition() && !isCupCompetition()) 
            throw new UnknownCompetitionTypeException();
        
        if(isCupCompetition() && !isPotencyOfTwo())
            throw new NumberOfTeamsException("This CUP competition has not "
                    + "a POTNECY OF TWO number of teams");
        
        
        if(isLeagueCompetition() && !isPair())
            throw new NumberOfTeamsException("This LEAGUE competition has not "
                    + "a PAIR number of teams");
        
        
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
    
    private boolean isPotencyOfTwo(){ 
        int num = competition.getTeams().size();
        return (num != 0) && ((num & (num - 1)) == 0);
    }
    
    private boolean isPair() throws Exception {
        return competition.getTeams().size() % 2 == 0;
    }
    
    private boolean isCupCompetition(){
        return competition.getType() == Type.CUP;
    }
    
    private boolean isLeagueCompetition(){
        return competition.getType() == Type.LEAGUE;
    }
    
}
