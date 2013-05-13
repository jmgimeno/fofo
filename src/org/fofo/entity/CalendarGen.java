package org.fofo.entity;

import java.util.Date;
import java.util.List;
import org.fofo.dao.*;
import org.joda.time.DateTime;

/**
 * @author Jordi, Anatoli
 */
public class CalendarGen {
    private Competition competition;   
    private CalendarGen generator;
    private CalendarDAO calDao;
    
    public CalendarGen(){       
        this.competition= null;
        this.calDao = new CalendarDAOImpl();
    }
    
    public CalendarGen(Competition competition) throws Exception{
        this.competition= competition;      
        this.calDao = new CalendarDAOImpl();
    }
    
    public FCalendar CalculateCalendar() throws Exception{                
        checkRequiriments();        
        FCalendar cal;
        
        if(isLeagueCompetition()) cal = CalculateCalendarLeague();
        else cal = CalculateCalendarCup();
        
        calDao.addCalendar(cal);
        return cal;
    }
    
    public FCalendar CalculateCalendarCup() throws Exception{     
        checkRequiriments();        
                
        generator = new CalendarCupGen(competition);
        FCalendar cal = generator.CalculateCalendar();           
        
        calDao.addCalendar(cal);
        return cal;
    }
    
    public FCalendar CalculateCalendarLeague() throws Exception{    
        checkRequiriments();        
                
        generator = new CalendarLeagueGen(competition);
        FCalendar cal = generator.CalculateCalendar();
        
        calDao.addCalendar(cal);
        return cal;
    }    
    

    public Competition getCompetition(){
        return this.competition;
    }
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
    
    /* PRIVATE OPS */
    
    private void checkRequiriments() throws Exception {
       //if(competition == null) throw new UnknownCompetitionTypeException();
        
       //D'aquesta forma no tens que chequellar el tipus de competicio 
       //a CalculateCalendarCup ni a CalculateCalendarLeague         
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
