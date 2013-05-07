package org.fofo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Anatoli, Mohamed
 */
public class FCalendar {
    
    private Competition competition;
    private List<WeekMatches> calendar;
    
    public FCalendar(){
        this.competition = null;
        this.calendar = new ArrayList<WeekMatches>();        
    }
       
    public FCalendar(Competition c){
        this.competition = c;
        this.calendar = new ArrayList<WeekMatches>();
    }   
    
    public void createCalendar() throws Exception{
 /*      
        if(competition == null) throw new UnknownCompetitionTypeException();

        if(competition.getType()==Type.LEAGUE)
            calendar = new CalendarLeagueGen(competition).getCalendar();
        else if(competition.getType()==Type.CUP)
            calendar = new CalendarCupGen(competition).CalculateCalendar();
        else
            throw new UnknownCompetitionTypeException(); 
*/      
    }
    
    public void setCompetition(Competition c){
        this.competition = c;
    }
    
    public void setWeekMatches(List<WeekMatches> wm){
        this.calendar = wm;
    }
    
    public List<WeekMatches> getAllWeekMatches() {
        return calendar;
    }
    
    public WeekMatches getWeekMatches(int index){
        return calendar.get(index);
    }
    
    public int getNumOfWeekMatches(){
        return calendar.size();
    }
        
    
    
}
